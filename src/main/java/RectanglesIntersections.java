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

    public static void main(String[] args) throws InvalidParameterException, JsonSyntaxException {
        // Create 2d LinkedList where first dimension is the level of intersections
        LinkedList<LinkedList<Rectangle>> rectangles = new LinkedList<>();
        int intersectionLevel = 0;

        if (args[0] == null) {
            throw new InvalidParameterException("Path to json file as argument required");
        }

        try {
            rectangles.add(parseRectanglesFromJson(args[0]));
        } catch (InvalidParameterException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Input:");
        for (var rec : rectangles.get(intersectionLevel)) {
            System.out.println(rec);
        }

        System.out.println("\nOutput:");
        int intersectionRectangleCounter = 1;

        /*
        On each level we intersect each rectangle in that list with every other one
        The resulting intersections fill up the next level of the 2d LinkedList
         */
        while (rectangles.size() - 1 == intersectionLevel) {
            ++intersectionLevel;
            for (int i = 0; i < rectangles.get(intersectionLevel - 1).size() - 1; ++i) {
                for (int j = i + 1; j < rectangles.get(intersectionLevel - 1).size(); j++) {
                    RectangleFromIntersection result = Rectangle.calculateIntersection(
                            rectangles.get(intersectionLevel - 1).get(i),
                            rectangles.get(intersectionLevel - 1).get(j));
                    if (result != null) {
                        if (rectangles.size() == intersectionLevel) {
                            rectangles.add(new LinkedList<>());
                        }
                        LinkedList<Rectangle> parents = result.getParents();
                        // Check if there are no other rectangles with same parents
                        // (Different permutation of same intersection)
                        // If so add the rectangle
                        if (new LinkedList<>(rectangles.get(intersectionLevel)).stream().noneMatch(rectangle -> {
                            var r = (RectangleFromIntersection) rectangle;
                            return r.getParents().equals(parents);
                        })) {
                            result.number = intersectionRectangleCounter++;
                            rectangles.get(intersectionLevel).add(result);
                        }
                    }
                }
            }
        }

        /*
        Output the intersections
         */
        for (int i = 1; i < rectangles.size(); ++i) {
            var level = rectangles.get(i);
            for (var rec : level) {
                System.out.println(rec);
            }
        }
    }
}

