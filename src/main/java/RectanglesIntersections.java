import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

public class RectanglesIntersections {
    static class JsonRectangle {
        int x, y, w, h;
        boolean xSet;
        boolean ySet;
        boolean wSet;
        boolean hSet;

        public boolean isXSet() {
            return xSet;
        }

        public boolean isYSet() {
            return ySet;
        }

        public boolean isWSet() {
            return wSet;
        }

        public boolean isHSet() {
            return hSet;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
            xSet = true;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
            ySet = true;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
            wSet = true;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
            hSet = true;
        }
    }

    static class JsonWrapperObject {
        JsonRectangle[] recs;

        public JsonRectangle[] getRecs() {
            return recs;
        }
    }

    private static String readFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return contentBuilder.toString();
    }


    static LinkedList<Rectangle> parseRectangleFromJsonString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        try {
            JsonWrapperObject data = mapper.readValue(jsonString, JsonWrapperObject.class);
            int counter = 1;

            for (var rec : data.recs) {
                if (rec != null) {
                    if(rec.isWSet() && rec.isHSet() && rec.isXSet() && rec.isYSet()) {
                        rectangles.add(new Rectangle(new Point2D(rec.x, rec.y), rec.w, rec.h, counter++));
                    } else {
                        System.out.println("Not adding ill defined rectangle");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rectangles;
    }

    static LinkedList<Rectangle> parseRectanglesFromJsonFile(String filename){
        String filePath = new File("").getAbsolutePath();
        filePath += "/" + filename;

        return parseRectangleFromJsonString(readFile(filePath));
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

        LinkedList<Rectangle> rectangles = (parseRectanglesFromJsonFile(args[0]));

        System.out.println("\nInput:");
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

