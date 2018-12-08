package Scoring;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class convert {
	public static void mp3towav(String fileName, String outfileName) throws JavaLayerException, UnsupportedAudioFileException, IOException, InterruptedException{

		Converter cvt = new Converter();
		System.out.println(outfileName);
		cvt.convert(fileName, outfileName);
		System.out.println("Converter finished");
	}
}
