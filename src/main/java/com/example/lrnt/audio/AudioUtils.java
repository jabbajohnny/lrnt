package com.example.lrnt.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioUtils {

    public static AudioInputStream convertToWav(AudioInputStream audio) {

        AudioFormat format = audio.getFormat();

        AudioFormat pcmFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                format.getSampleRate(),
                16,
                format.getChannels(),
                format.getChannels() * 2,
                format.getSampleRate(),
                false);

        return AudioSystem.getAudioInputStream(pcmFormat, audio);
    }
}
