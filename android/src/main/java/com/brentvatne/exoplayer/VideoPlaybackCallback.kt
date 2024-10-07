package com.brentvatne.exoplayer

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Bundle
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.brentvatne.exoplayer.VideoPlaybackService.Companion.COMMAND
import com.brentvatne.exoplayer.VideoPlaybackService.Companion.commandFromString
import com.brentvatne.exoplayer.VideoPlaybackService.Companion.handleCommand
import com.google.common.util.concurrent.ListenableFuture
import com.brentvatne.common.toolbox.DebugLog


class VideoPlaybackCallback(var audioManager: AudioManager) : MediaSession.Callback {
    override fun onConnect(session: MediaSession, controller: MediaSession.ControllerInfo): MediaSession.ConnectionResult {
        DebugLog.w("VideoPlaybackCallback", "onConnect")
        try {
            return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                .setAvailablePlayerCommands(
                    MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
                        .add(Player.COMMAND_SEEK_FORWARD)
                        .add(Player.COMMAND_SEEK_BACK)
                        .build()
                ).setAvailableSessionCommands(
                    MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                        .add(SessionCommand(COMMAND.SEEK_FORWARD.stringValue, Bundle.EMPTY))
                        .add(SessionCommand(COMMAND.SEEK_BACKWARD.stringValue, Bundle.EMPTY))
                        .build()
                )
                .build()
        } catch (e: Exception) {
            return MediaSession.ConnectionResult.reject()
        }
    }

    fun onPlay() {
        DebugLog.w("VideoPlaybackCallback", "play")
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val audioFocusRequest: AudioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            // .setOnAudioFocusChangeListener(audioFocusChangeListener) // Handle audio focus changes
            .build()

        // Request audio focus here
//        if (this.audioManager.requestAudioFocus(audioFocusRequest) === AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback
//        }
    }

    fun onPause() {
        DebugLog.w("VideoPlaybackCallback", "pause")
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val audioFocusRequest: AudioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            // .setOnAudioFocusChangeListener(audioFocusChangeListener) // Handle audio focus changes
            .build()

        // Handle pause, release audio focus if needed
//        audioManager.abandonAudioFocusRequest(audioFocusRequest)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        DebugLog.w("VideoPlaybackCallback", "onCustomCommand")
        handleCommand(commandFromString(customCommand.customAction), session, audioManager)
        return super.onCustomCommand(session, controller, customCommand, args)
    }
}
