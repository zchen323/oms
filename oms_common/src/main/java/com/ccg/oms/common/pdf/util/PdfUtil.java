package com.ccg.oms.common.pdf.util;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ITextString;
import org.pdfclown.documents.contents.TextChar;
import org.pdfclown.documents.interaction.annotations.TextMarkup;
import org.pdfclown.documents.interaction.annotations.TextMarkup.MarkupTypeEnum;
import org.pdfclown.files.SerializationModeEnum;
import org.pdfclown.tools.TextExtractor;
import org.pdfclown.util.math.Interval;
import org.pdfclown.util.math.geom.Quad;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfUtil {
	
	public static void extractSelectPageIntoNewFile(File originalPdfFile, File newFile, List<Integer> pagesNumbers) throws Exception{
		InputStream is = new FileInputStream(originalPdfFile);
		OutputStream os = new FileOutputStream(newFile);
	
		PdfReader reader = new PdfReader(is);
		reader.selectPages(pagesNumbers);
		PdfStamper stamper = new PdfStamper(reader, os);		
		stamper.close();
		
		PdfReader newreader = new PdfReader(newFile.getAbsolutePath());
		int numberOfPages = newreader.getNumberOfPages();
		newreader.close();
		if(numberOfPages == 0){
			// copy theend.pdf to outptu file
			pagesNumbers = new ArrayList<Integer>();
			pagesNumbers.add(1);
			is = PdfUtil.class.getResourceAsStream("theend.pdf");
			os = new FileOutputStream(newFile);
			reader = new PdfReader(is);
			reader.selectPages(pagesNumbers);
			stamper = new PdfStamper(reader, os);
			stamper.close();
			is.close();
		}
	}
	
	public static void textHighlight(File originalFile, File newFile, String textRegEx) throws IOException{
		
		System.out.println("===== highlight regex ======");
		System.out.println(textRegEx);
		
		org.pdfclown.files.File file = new org.pdfclown.files.File(originalFile.getAbsolutePath());
		
		Pattern pattern = Pattern.compile(textRegEx, Pattern.CASE_INSENSITIVE);
		TextExtractor textExtractor = new TextExtractor(true, true);
		
		for (final Page page : file.getDocument().getPages()) {
			//System.out.println("\nScanning page " + (page.getIndex() + 1) + "...\n");

			// 2.1. Extract the page text!
			Map<Rectangle2D, List<ITextString>> textStrings = textExtractor.extract(page);

			// 2.2. Find the text pattern matches!
			final Matcher matcher = pattern.matcher(TextExtractor.toString(textStrings));

			// 2.3. Highlight the text pattern matches!
			textExtractor.filter(textStrings, new TextExtractor.IIntervalFilter() {
				@Override
				public boolean hasNext() {
					return matcher.find();
				}

				@Override
				public Interval<Integer> next() {
					return new Interval<Integer>(matcher.start(), matcher.end());
				}

				@Override
				public void process(Interval<Integer> interval, ITextString match) {
					// Defining the highlight box of the text pattern match...
					List<Quad> highlightQuads = new ArrayList<Quad>();
					{
						/*
						 * NOTE: A text pattern match may be split across
						 * multiple contiguous lines, so we have to define a
						 * distinct highlight box for each text chunk.
						 */
						Rectangle2D textBox = null;
						for (TextChar textChar : match.getTextChars()) {
							Rectangle2D textCharBox = textChar.getBox();
							if (textBox == null) {
								textBox = (Rectangle2D) textCharBox.clone();
							} else {
								if (textCharBox.getY() > textBox.getMaxY()) {
									highlightQuads.add(Quad.get(textBox));
									textBox = (Rectangle2D) textCharBox.clone();
								} else {
									textBox.add(textCharBox);
								}
							}
						}
						highlightQuads.add(Quad.get(textBox));
					}
					// Highlight the text pattern match!
					new TextMarkup(page, null, MarkupTypeEnum.Highlight, highlightQuads);
				}

				@Override
				public void remove() {
					// TODO Auto-generated method stub
					
				}
			});
			
			file.save(newFile, SerializationModeEnum.Standard);	
		}		
	}
	
	
	
	public static void main(String[] args) throws DocumentException, IOException {
			
		///// test pdf text highlight //////
		String originalFilePath = "/Users/zchen323/codebase/test_doc/partical.pdf";
		String highlightedFilePath = "/Users/zchen323/codebase/test_doc/partical_highlighted3.pdf";
		String textRegEx = "Troubleshooting|FEDITC|alleviate the potential";
		
		File os = new File(highlightedFilePath);
		File is = new File(originalFilePath);
		
		PdfUtil.textHighlight(is, os, textRegEx);
		//is.close();
		//os.flush();
		//os.close();
	}

}