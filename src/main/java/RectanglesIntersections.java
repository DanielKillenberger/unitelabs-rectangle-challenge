import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

public class RectanglesIntersections {

    private static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));

        return contentBuilder.toString();
    }


    static LinkedList<Rectangle> parseRectanglesFromJsonString(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        JsonWrapperObject data = mapper.readValue(jsonString, JsonWrapperObject.class);
        int counter = 1;
        for (var rec : data.recs) {
            if (rec != null) {
                if (rec.isWSet() && rec.isHSet() && rec.isXSet() && rec.isYSet()) {
                    rectangles.add(new Rectangle(new Point2D(rec.x, rec.y), rec.w, rec.h, counter++));
                } else {
                    System.out.println("Not adding ill defined rectangle");
                }
            }
        }
        return rectangles;
    }

    static LinkedList<Rectangle> parseRectanglesFromJsonFile(String filename) throws IOException{
        String filePath = new File("").getAbsolutePath();
        filePath += "/" + filename;

        return parseRectanglesFromJsonString(readFile(filePath));
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

        LinkedList<Rectangle> flattenedIntersections = new LinkedList<>();
        for (var list : intersectionRectangles) {
            flattenedIntersections.addAll(list);
        }
        return flattenedIntersections;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Path to json file as argument required - terminating");
            System.exit(1);
        }

        LinkedList<Rectangle> rectangles = null;
        try {
            rectangles = (parseRectanglesFromJsonFile(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (rectangles == null) {
            System.out.println("Received no valid input rectangles");
            System.exit(1);
        } else {
            System.out.println("\nInput:");
            for (var rec : rectangles) {
                System.out.println(rec);
            }
        }

        LinkedList<Rectangle> intersectionRectangles = calculateIntersections(rectangles);

        System.out.println("\nOutput:");
        for (var rec : intersectionRectangles) {
            System.out.println(rec);
        }
    }
}

