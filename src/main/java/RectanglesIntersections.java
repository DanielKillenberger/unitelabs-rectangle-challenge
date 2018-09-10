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

    public static void main(String[] args) throws InvalidParameterException, JsonSyntaxException {
        Gson gson = new Gson();

        // Create 2d LinkedList where first dimension is the level of intersections
        // (amount of rectangles intersecting)
        LinkedList<LinkedList<Rectangle>> rectangles= new LinkedList<>();
        int intersectionLevel = 0;

        if(args[0] == null) {
            throw new InvalidParameterException("Path to json file as argument required");
        }
        try {
            String filePath = new File("").getAbsolutePath();
            filePath += "/" + args[0];
            JsonReader reader = new JsonReader(new FileReader(filePath));
            JsonWrapperObject data = gson.fromJson(reader, JsonWrapperObject.class);

            int counter = 1;
            for (var rec : data.recs) {
                if (rec != null) {
                    if(rectangles.size() == intersectionLevel) {
                        rectangles.add(new LinkedList<>());
                    }
                    rectangles.get(intersectionLevel).add(new Rectangle(new Vector2D(rec.x, rec.y), rec.w, rec.h, counter++));
                }
            }
        } catch (FileNotFoundException e) {
            throw new InvalidParameterException("Given parameter is not a valid file path");
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Badly formatted json file - terminating");
        }

        System.out.println("Input:");
        for(var rec : rectangles.get(intersectionLevel)) {
            System.out.println(rec);
        }

        System.out.println("\nOutput:");
        int intersectionRectangleCounter = 1;

        while(rectangles.size() - 1 == intersectionLevel) {
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
                        // If so add the rectangle
                        if (new LinkedList<>(rectangles.get(intersectionLevel)).stream().noneMatch(rectangle -> {
                            var r = (RectangleFromIntersection)rectangle;
                            return r.getParents().equals(parents);
                        })) {
                            result.number = intersectionRectangleCounter++;
                            rectangles.get(intersectionLevel).add(result);
                        }
                    }
                }
            }
        }

        for (var level : rectangles) {
            for(var rec : level) {
                System.out.println(rec);
            }
        }
    }
}

