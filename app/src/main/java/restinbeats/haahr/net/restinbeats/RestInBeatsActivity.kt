package restinbeats.haahr.net.restinbeats

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.VideoView


class RestInBeatsActivity : AppCompatActivity() {

    private lateinit var backgroundVideoUri : String

    private lateinit var musicPlayer : MediaPlayer
    private lateinit var videoView : VideoView

    private lateinit var playPauseButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_in_beats)
        playPauseButton = findViewById(R.id.play_pause_button)
        backgroundVideoUri = "android.resource://$packageName/${R.raw.video_background}"
        createMediaPlayer()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadVideoBackground()
    }

    private fun createMediaPlayer(){
        musicPlayer = MediaPlayer.create(this, R.raw.octagon)
    }

    private fun loadVideoBackground(){
        videoView = findViewById(R.id.background_video)
        val uri = Uri.parse(backgroundVideoUri)
        videoView.setVideoURI(uri)
        videoView.start()
        videoView.setOnPreparedListener{ videoPlayer: MediaPlayer -> onVideoPrepared(videoPlayer) }
    }

    private fun onCompleted() {
        musicPlayer.prepare()
        musicPlayer.start()
    }

    private fun onPlayPauseClicked() {
        if (musicPlayer.isPlaying){
            musicPlayer.pause()
            playPauseButton.setBackgroundResource(R.drawable.play_button)
        }
        else{
            musicPlayer.start()
            playPauseButton.setBackgroundResource(R.drawable.pause_button)
        }
    }

    private fun onReplayClicked() {
        musicPlayer.stop()
        musicPlayer.prepare()
        playPauseButton.setBackgroundResource(R.drawable.play_button)
    }

    private fun onVideoPrepared(videoPlayer: MediaPlayer) {
        videoPlayer.isLooping = true
    }

    private fun setupListeners() {
        playPauseButton.setOnClickListener{ onPlayPauseClicked() }

        val replayButton = findViewById<ImageButton>(R.id.replay_button)
        replayButton.setOnClickListener{ onReplayClicked() }

        musicPlayer.setOnCompletionListener { onCompleted() }
    }
}
