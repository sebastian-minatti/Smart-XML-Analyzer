package sminatti.common;

import org.jsoup.nodes.Element;

public class Util {

	public static void printElement(Element element) {
        if (element == null) {
            System.out.println("THE ELEMENT: null");
        } else {
            System.out.println("PATH TO THE ELEMENT: " + getPathToElement(element));
            System.out.println("THE ELEMENT: " + element);
        }
    }
	
    private static String getPathToElement(Element currentElement) {
        StringBuilder result = new StringBuilder();
        for (int i = currentElement.parents().size() - 1; i >= 0; i--) {
            Element parent = currentElement.parents().get(i);
            appendOneElementToPath(result, parent);
            result.append(" >");
        }
        appendOneElementToPath(result, currentElement);
        return result.toString();
    }	
    
    private static void appendOneElementToPath(StringBuilder result, Element element) {
        result.append(" ");
        result.append(element.tag());
        if (element.id() != null && element.id().length() > 0) {
            result.append("#");
            result.append(element.id());
        }
        if (element.tag().getName().equals("a")) {
            String href = element.attr("href");
            if (href != null) {
                result.append(" href=\"");
                result.append(href);
                result.append("\"");
            }
        }
    }    
}
