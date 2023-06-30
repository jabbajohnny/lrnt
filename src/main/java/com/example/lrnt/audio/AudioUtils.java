package com.example.lrnt.audio;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;

import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class AudioUtils {

    public static AudioInputStream convertToWav(Path path) throws UnsupportedAudioFileException, IOException {

        AudioInputStream audio = AudioSystem.getAudioInputStream(path.toFile());
        AudioFormat format = audio.getFormat();

        AudioFormat pcmFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                format.getSampleRate(),
                16,
                format.getChannels(),
                format.getChannels() * 2,
                format.getFrameRate(),
                false);

        AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(pcmFormat, audio);
        AudioInputStream stream = new AudioInputStream(audio, pcmFormat, decodeMP3(path).calculate_framesize());

        return stream;
    }

    public static Header decodeMP3(Path path) {
        try {
            Bitstream bitstream = new Bitstream(new FileInputStream(path.toFile()));
            Decoder decoder = new Decoder();

            return bitstream.readFrame();
        } catch (FileNotFoundException | BitstreamException e) {
            throw new RuntimeException(e);
        }
    }
}
