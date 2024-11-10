package com.example.hwaa.presentation.fragment.main.dictionary

object WebProvider {
    fun getYouGlishWidget(word: String): String {
        return """
            <!DOCTYPE html>
            <html>
              <body>
                <div id="widget-1"></div>
                <div style="display: flex; justify-content: center; gap: 20px; margin-top: 10px;">
                  <button id="prevBtn" onclick="previousVideo()">
                    Previous
                  </button>
                  <button id="playPauseBtn" onclick="togglePlayPause()">
                    Pause
                  </button>
                  <button id="nextBtn" onclick="nextVideo()">
                    Next
                  </button>
                </div>
                <script>
                  var tag = document.createElement('script');
                  tag.src = "https://youglish.com/public/emb/widget.js";
                  var firstScriptTag = document.getElementsByTagName('script')[0];
                  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                  var widget;
                  var isPlaying = true;
                  function onYouglishAPIReady() {
                    widget = new YG.Widget("widget-1", {
                      width: 640,
                      components: 8,
                      events: {
                        'onFetchDone': onFetchDone,
                        'onVideoChange': onVideoChange,
                        'onCaptionConsumed': onCaptionConsumed
                      }
                    });
                    widget.fetch("$word", "chinese");
                  }

                  var views = 0, curTrack = 0, totalTracks = 0;

                  function onFetchDone(event) {
                    totalTracks = event.totalResult;
                    updateButtonStates();
                  }

                  function onVideoChange(event) {
                    curTrack = event.trackNumber;
                    views = 0;
                    updateButtonStates();
                  }

                  function onCaptionConsumed(event) {
                    if (++views < 3)
                      widget.replay();
                    else 
                      if (curTrack < totalTracks)
                        widget.next();
                  }

                  function previousVideo() {
                    if (curTrack > 1) {
                      widget.previous();
                    }
                  }

                  function nextVideo() {
                    if (curTrack < totalTracks) {
                      widget.next();
                    }
                  }

                  function togglePlayPause() {
                    if (isPlaying) {
                      widget.pause();
                      document.getElementById('playPauseBtn').textContent = 'Play';
                    } else {
                      widget.play();
                      document.getElementById('playPauseBtn').textContent = 'Pause';
                    }
                    isPlaying = !isPlaying;
                  }

                  function updateButtonStates() {
                    const prevBtn = document.getElementById('prevBtn');
                    const nextBtn = document.getElementById('nextBtn');
                  }
                </script>
              </body>
            </html>
""".trimIndent()
    }
}
