class AudioManager {
    constructor(trackPath) {
        this.audio = new Audio(trackPath);
        this.audio.loop = true;
        this.audio.volume = 0.5;
    }

    play() {
        this.audio.play().catch(e => console.log("Autoplay prevented"));
    }

    pause() {
        this.audio.pause();
    }

    setVolume(volume) {
        this.audio.volume = volume;
    }
}
