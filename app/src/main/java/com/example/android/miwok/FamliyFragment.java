package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamliyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    //for audio focus
    private AudioManager audioManager;

    //this listener defines what should be done with our audio when audio is lost or gained again

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                // Pause playback immediately
                mediaPlayer.stop();
                releaseMediaPlayer();
            }
            else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mediaPlayer.start();
            }
        }
    };
    //What to do next when playing audio is completed
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_words, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        /*Make this arraylist final & declare here. Earlier I had declared outside create method & it was adding
        words repeatedly */
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father ));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother ));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son ));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter ));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother ));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother ));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister ));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister ));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother ));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandmother, R.raw.family_grandfather ));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                //get audio focus to play our audio. So e.g. music is being played then it will be paused
                //and as soon as we complete playing our track, we will release AF so music player will start playing song again

                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                Word currentWord = words.get(position);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer=MediaPlayer.create(getActivity(), currentWord.getMusicResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer () {
        if(mediaPlayer != null ){
            mediaPlayer.release();

            mediaPlayer = null;
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

}
