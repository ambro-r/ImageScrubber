package is;

import is.objects.ImageMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MetaDataReader {

    private final Logger LOG = LoggerFactory.getLogger(MetaDataReader.class);

    public List<ImageMetaData> readImageMetaData(File imageFile) {
        LOG.info("Reading metadata from file... {}.", imageFile.getName());
        List<ImageMetaData> imageMetaDataList = new ArrayList<>();
        try {
          ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
          Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
          if (readers.hasNext()) {
              ImageReader reader = readers.next();
              LOG.debug("\t...Getting Image Reader with format {}", reader.getFormatName());

              // attach source to the reader
              reader.setInput(imageInputStream, true);

              // read metadata of first image
              IIOMetadata metadata = reader.getImageMetadata(0);

              String[] formatNames = metadata.getMetadataFormatNames();
              LOG.debug("\t...{} formats found in image.", formatNames.length);
              for (int i = 0; i < formatNames.length; i++) {
                  LOG.info("\t...processing format {}/{} with name: {}", i + 1, formatNames.length, formatNames[i]);
                  imageMetaDataList.add(getImageMetaData(metadata.getAsTree(formatNames[i])));
              }
          }
        } catch (IOException ioe) {
            LOG.error("Exception occurred on IO: {}", ioe.getMessage());
        }
        return imageMetaDataList;
    }

    private String getIndentation(int indent) {
        StringBuffer indentation = new StringBuffer();
        for(int i = 0; i < indent; i++) {
            indentation.append("\t");
        }
        return indentation.toString();
    }

    private ImageMetaData getImageMetaData(Node node) {
        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setAttributes(getImageMetaData(node, 0));
        LOG.info("\t...{} attributes found in format.", imageMetaData.getAttributes().size());
        return imageMetaData;
    }

    private HashMap<String, String> getImageMetaData(Node node, int indent) {
        String indentation = getIndentation(indent);
        HashMap<String, String> attributes = new HashMap<>();

        LOG.debug("{}Processing node [{}]....", indentation, node.getNodeName());
        NamedNodeMap namedNodeMap = node.getAttributes();
        LOG.debug("{}...{} attributes found.", indentation, (namedNodeMap != null) ? namedNodeMap.getLength() : 0);
        if (namedNodeMap != null) { ;
            for (int i = 0; i < namedNodeMap.getLength(); i++) {
                Node attribute = namedNodeMap.item(i);
                LOG.trace("{}\t[type={}] {}={}", indentation, attribute.getNodeType(), attribute.getNodeName(), attribute.getNodeValue());
                attributes.put((node.getNodeName() + "." + attribute.getNodeName()).toLowerCase(), attribute.getNodeValue());
            }
        }

        NodeList children = node.getChildNodes();
        LOG.debug("{}...{} children found.", indentation, (children != null) ? children.getLength() : 0);
        if(children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                attributes.putAll(getImageMetaData(child, indent + 1));
            }
        }
        return attributes;
    }
}
