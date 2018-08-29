package sminatti.core;

import java.io.IOException;

import org.jsoup.nodes.Element;

public interface AnalyzerStrategy {

	Element findElements(String originFilePath, String sampleFilePath, String originElementId) throws IOException;
}
