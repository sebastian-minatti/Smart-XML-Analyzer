package sminatti.app;

import java.io.IOException;

import org.jsoup.nodes.Element;

import sminatti.common.Util;
import sminatti.core.AnalyzerStrategy;
import sminatti.core.XmlAnalyzer;

public class Main {

    static final String DEFAULT_TARGET_ELEMENT_ID = "make-everything-ok-button";
    
    public static void main(String[] args) throws IOException {

        String originFilePath;
        String sampleFilePath;
        String originalElementId = DEFAULT_TARGET_ELEMENT_ID;

        switch (args.length) {
            case 2:
                originFilePath = args[0];
                sampleFilePath = args[1];
                break;
            case 3:
                originFilePath = args[0];
                sampleFilePath = args[1];
                originalElementId = args[2];
                break;
            default:
                throw new IllegalArgumentException("Check arguments numbers, TWO or THREE arguments are allowed!");
        }
        System.out.println("\nOriginal file: " + originFilePath);
        System.out.println("Sample file: " + sampleFilePath);
        System.out.println("Original HTML element ID (in origin file): " + originalElementId);

        AnalyzerStrategy analyzer = new XmlAnalyzer();

        Element elements = analyzer.findElements(
                originFilePath,
                sampleFilePath,
                originalElementId
        );

        System.out.println("\nSIMILAR HTML ELEMENT (from " + sampleFilePath + "):");
        Util.printElement(elements);
    }

}
