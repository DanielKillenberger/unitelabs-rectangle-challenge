import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

public class RectanglesIntersections {
    private class JsonRectangle {
        int x, y, w, h;
    }

    private class JsonWrapperObject {
        JsonRectangle[] recs;
    }

    public static void main(String[] args) throws InvalidParameterException, JsonSyntaxException {
        Gson gson = new Gson();
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        try {
            String filePath = new File("").getAbsolutePath();
            filePath += "/" + args[0];
            JsonReader reader = new JsonReader(new FileReader(filePath));
            JsonWrapperObject data = gson.fromJson(reader, JsonWrapperObject.class);

            for (var rec : data.recs) {
                if (rec != null) {
                    rectangles.add(new Rectangle(new Vector2D(rec.x, rec.y), rec.w, rec.h));
                }
            }
        } catch (FileNotFoundException e) {
            throw new InvalidParameterException("Given parameter is not a valid file path");
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Badly formatted json file - terminating");
        }

        LinkedList<RectangleFromIntersection> rectanglesFromIntersections= new LinkedList<>();
        for(int i = 0; i < rectangles.size()-1; ++i) {
            for(int j = i+1; j < rectangles.size(); j++) {
                RectangleFromIntersection result = Rectangle.calculateIntersection(rectangles.get(i), rectangles.get(j));
                if(result != null) {
                    rectanglesFromIntersections.add(result);
                }
            }
        }

        System.out.println(rectanglesFromIntersections);
    }
}

