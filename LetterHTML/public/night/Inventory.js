class Inventory {
    constructor() {
        this.letters = [];
        this.item = null; // Changed from items array to a single item
    }

    addLetter(letter) {
        if (this.letters.length < 2) { // Limit to 2 letters
            this.letters.push(letter);
            return true; // Indicate success
        }
        return false; // Indicate failure (inventory full)
    }

    addItem(item) {
        this.item = item; // Set the single item
    }

    getLetters() {
        return this.letters;
    }

    getItem() {
        return this.item; // Return the single item
    }

    clear() {
        this.letters = [];
        this.item = null; // Clear the item
    }
}