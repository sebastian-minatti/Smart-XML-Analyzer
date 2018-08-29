package sminatti.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import sminatti.common.Util;

public class XmlAnalyzer implements AnalyzerStrategy{

     	private static final String CHARSET_NAME = "utf8";

	    private NormalizedStringDistance algorithm = new NormalizedLevenshtein();

		@Override
		public Element findElements(String originFilePath, String sampleFilePath, String originElementId) throws IOException {
	        Element originalElement = findElementById(new File(originFilePath), originElementId);
	        if (originalElement == null)
	            throw new IllegalStateException(
	                    "Not found element with id=" + originElementId + " in " + originFilePath);

	        System.out.println("\nORIGINAL HTML ELEMENT (from " + originFilePath + " ):");
	        Util.printElement(originalElement);

	        Tag originTag = originalElement.tag();
	        List<Element> sameTagElements = getElementsWithSimilarTag(new File(sampleFilePath), originTag);

	        // get all attributes from target element
	        String originAllAttrs = originalElement.attributes()
	        		.asList()
	        		.stream()
	                .map(attr -> attr.getKey() + " = " + attr.getValue())
	                .collect(Collectors.joining(", "));
	        
	        return distanceMetric(originAllAttrs, sameTagElements);
	    }

	    private Element distanceMetric(String etalon, List<Element> toCompare) {

	        double minDistance = 1.0F;
	        Element closestElement = null;

	        for (Element compareElement : toCompare) {
	            double compareDistance = algorithm.distance(
	                    etalon,
	                    joinAttributesByElement(compareElement)
	            );
	            if (compareDistance < minDistance) {
	                minDistance = compareDistance;
	                closestElement = compareElement;
	            }
	        }

	        return closestElement;
	    }

	    private static List<Element> getElementsWithSimilarTag(File htmlFile, Tag originTag) throws IOException {
	        Document doc = Jsoup.parse(
	                htmlFile,
	                CHARSET_NAME,
	                htmlFile.getAbsolutePath());

	        Elements elementsOriginTag = doc.select(originTag.getName());
	        List<Element> result = new ArrayList<>();
	        for (Element curElement : elementsOriginTag) {
	            result.add(curElement);
	        }
	        return result;
	    }

	    private static String joinAttributesByElement(Element element) {
	        return element.attributes().asList().stream()
	                .map(attr -> attr.getKey() + " = " + attr.getValue())
	                .collect(Collectors.joining(", "));
	    }

	    private static Element findElementById(File htmlFile, String targetElementId) throws IOException {
	        Document doc = Jsoup.parse(
	                htmlFile,
	                CHARSET_NAME,
	                htmlFile.getAbsolutePath());

	        return doc.getElementById(targetElementId);
	    }
}
