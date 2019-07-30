package is.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class ImageMetaData {

    @Getter @Setter
    private HashMap<String, String> attributes = new HashMap<>();

    @Override
    public String toString() {
        return new StringBuilder("ImageMetaData [")
                .append("attributes=").append(attributes.toString())
                .append("]")
                .toString();
    }
}
