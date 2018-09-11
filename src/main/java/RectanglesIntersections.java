import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.security.InvalidParameterException;
import java.util.LinkedList;

public class RectanglesIntersections {
    private class JsonRectangle {
        int x, y, w, h;
    }

    private class JsonWrapperObject {
        JsonRectangle[] recs;
    }

    private static LinkedList<Rectangle> parseRectanglesFromJson(String filename) {
        Gson gson = new Gson();
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        try {
            String filePath = new File("").getAbsolutePath();
            filePath += "/" + filename;
            JsonReader reader = new JsonReader(new FileReader(filePath));
            JsonWrapperObject data = gson.fromJson(reader, JsonWrapperObject.class);

            int counter = 1;
            for (var rec : data.recs) {
                if (rec != null) {
                    rectangles.add(new Rectangle(new Vector2D(rec.x, rec.y), rec.w, rec.h, counter++));
                }
            }
        } catch (FileNotFoundException e) {
            throw new InvalidParameterException("Given parameter is not a valid file path - terminating");
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Badly formatted json file - terminating");
        }
        return rectangles;
    }

    static LinkedList<Rectangle> calculateIntersections(LinkedList<Rectangle> rectangles) {
        int intersectionLevel = 0;
        int intersectionRectangleCounter = 1;

        LinkedList<LinkedList<Rectangle>> intersectionRectangles = new LinkedList<>();
        // Add nonIntersected rectangles for simpler loop. Remove at the end.
        intersectionRectangles.add(rectangles);

        /*
        On each level we intersect each rectangle in that list with every other one.
        The resulting intersections fill up the next level of the 2d LinkedList.

        While loop terminates when no new level has been added to the 2d LinkedList
        in one complete iteration meaning no more additional intersections.
         */
        while (intersectionRectangles.size() - 1 == intersectionLevel) {
            ++intersectionLevel;
            for (int i = 0; i < intersectionRectangles.get(intersectionLevel - 1).size() - 1; ++i) {
                for (int j = i + 1; j < intersectionRectangles.get(intersectionLevel - 1).size(); ++j) {
                    RectangleFromIntersection result = Rectangle.calculateIntersection(
                            intersectionRectangles.get(intersectionLevel - 1).get(i),
                            intersectionRectangles.get(intersectionLevel - 1).get(j));
                    if (result != null) {
                        if (intersectionRectangles.size() == intersectionLevel) {
                            intersectionRectangles.add(new LinkedList<>());
                        }
                        LinkedList<Rectangle> parents = result.getParents();
                        // Check if there are no other rectangles with same parents
                        // (Different permutation of same intersection)
                        // If so add the rectangle
                        if (intersectionRectangles.get(intersectionLevel).stream().noneMatch(rectangle -> {
                            var r = (RectangleFromIntersection) rectangle;
                            return r.getParents().equals(parents);
                        })) {
                            result.number = intersectionRectangleCounter++;
                            intersectionRectangles.get(intersectionLevel).add(result);
                        }
                    }
                }
            }
        }
        intersectionRectangles.removeFirst();

        LinkedList<Rectangle> intersectionList = new LinkedList<>();
        for(var list : intersectionRectangles) {
            intersectionList.addAll(list);
        }
        return intersectionList;
    }

    public static void main(String[] args) throws InvalidParameterException, JsonSyntaxException {
        if (args.length == 0) {
            System.out.println("Path to json file as argument required - terminating");
            System.exit(1);
        }
        LinkedList<Rectangle> rectangles = new LinkedList<>();

        try {
            rectangles = (parseRectanglesFromJson(args[0]));
        } catch (InvalidParameterException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Input:");
        for (var rec : rectangles) {
            System.out.println(rec);
        }

        LinkedList<Rectangle> intersectionRectangles = calculateIntersections(rectangles);

        System.out.println("\nOutput:");
        for (var rec : intersectionRectangles) {
            System.out.println(rec);
        }
    }
}

