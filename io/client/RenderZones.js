class RenderZones {
    constructor(ctx) {
        this.ctx = ctx;
        this.zones = [
            { id: 'Common', color: 'SpringGreen', bounds: { x: [0, 2125], y: [0, 2000] } },
            { id: 'Unusual', color: 'blue', bounds: { x: [2500, 4625], y: [0, 2000] } },
            { id: 'Rare', color: 'green', bounds: { x: [5000, 7125], y: [0, 2000] } },
            { id: 'Epic', color: 'purple', bounds: { x: [7500, 9625], y: [0, 2000] } },
            { id: 'Legendary', color: 'red', bounds: { x: [10000, 12125], y: [0, 2000] } },
            { id: 'Mythic', color: 'cyan', bounds: { x: [12500, 14625], y: [0, 2000] } },
            { id: 'Ultra', color: 'pink', bounds: { x: [15000, 17125], y: [0, 2000] } },
            { id: 'Topi', color: 'Ivory', bounds: { x: [17500, 20000], y: [0, 2000] } }
        ];
        this.walls = [
            { bounds: { x: [2125, 2500], y: [0, 2000] } }, // Between common and unusual
            { bounds: { x: [4625, 5000], y: [0, 2000] } }, // Between unusual and rare
            { bounds: { x: [7125, 7500], y: [0, 2000] } }, // Between rare and epic
            { bounds: { x: [9625, 10000], y: [0, 2000] } }, // Between epic and legendary
            { bounds: { x: [12125, 12500], y: [0, 2000] } }, // Between legendary and mythic
            { bounds: { x: [14625, 15000], y: [0, 2000] } }, // Between mythic and ultra
            { bounds: { x: [17125, 17500], y: [0, 2000] } }  // Between ultra and ??? (last zone)
        ];
        this.portals = [
            { id: '1', position: [2075, 1000], linkedPortal: '2' },
            { id: '2', position: [2550, 1000], linkedPortal: '1' },
            { id: '3', position: [4575, 1000], linkedPortal: '4' },
            { id: '4', position: [5050, 1000], linkedPortal: '3' },
            { id: '5', position: [7075, 1000], linkedPortal: '6' },
            { id: '6', position: [7550, 1000], linkedPortal: '5' },
            { id: '7', position: [9575, 1000], linkedPortal: '8' },
            { id: '8', position: [10050, 1000], linkedPortal: '7' },
            { id: '9', position: [12075, 1000], linkedPortal: '10' },
            { id: '10', position: [12550, 1000], linkedPortal: '9' },
            { id: '11', position: [14575, 1000], linkedPortal: '12' },
            { id: '12', position: [15050, 1000], linkedPortal: '11' },
            { id: '13', position: [17075, 1000], linkedPortal: '14' },
            { id: '14', position: [17550, 1000], linkedPortal: '13' },
        ];
        this.animationFrame = 0; // Current frame for animation
        this.animationSpeed = 0.1; // Speed of the animation
        this.backgroundImage = new Image();
        this.backgroundImage.src = './res/GardenZ.png';

        this.imageLoaded = false;
        this.portalImage = new Image();
        this.portalImage.src = './res/portal.gif';

        this.portalImage.onload = () => {
            this.imageLoaded = true;
        }

        this.wallImage = new Image();
        this.wallImage.src = './res/wallpng.png'
    }

    draw(cameraOffsetX, cameraOffsetY) {
        // Draw the zones with their individual backgrounds
        this.ctx.strokeStyle = 'black'; // Border color for all zones
        this.ctx.lineWidth = 2; // Border width
        for (const zone of this.zones) {
            // Draw the background image for each zone
            if (this.imageLoaded) {
                const x1 = zone.bounds.x[0] - cameraOffsetX;
                const y1 = zone.bounds.y[0] - cameraOffsetY;
                const x2 = zone.bounds.x[1] - cameraOffsetX;
                const y2 = zone.bounds.y[1] - cameraOffsetY;

                // Draw the background image for the zone
                this.ctx.drawImage(this.backgroundImage, x1, y1, x2 - x1, y2 - y1);
            }

            // Draw the zone's border
            this.ctx.fillStyle = zone.color;
            this.ctx.beginPath();
            const x1Border = zone.bounds.x[0] - cameraOffsetX;
            const y1Border = zone.bounds.y[0] - cameraOffsetY;
            const x2Border = zone.bounds.x[1] - cameraOffsetX;
            const y2Border = zone.bounds.y[1] - cameraOffsetY;

            this.ctx.lineWidth = 10;

            this.ctx.rect(x1Border, y1Border, x2Border - x1Border, y2Border - y1Border); // Draw rectangle for zone
            this.ctx.stroke(); // Draw black border
        }

        // Draw walls
        this.drawWalls(cameraOffsetX, cameraOffsetY);

        // Draw portals
        this.drawPortals(cameraOffsetX, cameraOffsetY);
    }


    drawWalls(cameraOffsetX, cameraOffsetY) {
        for (const wall of this.walls) {
            const x1 = wall.bounds.x[0] - cameraOffsetX;
            const y1 = wall.bounds.y[0] - cameraOffsetY;
            const x2 = wall.bounds.x[1] - cameraOffsetX;
            const y2 = wall.bounds.y[1] - cameraOffsetY;

            // Draw the wall image
            this.ctx.drawImage(this.wallImage, x1, y1, x2 - x1, y2 - y1); // Draw wall image
        }
    }

    drawPortals(cameraOffsetX, cameraOffsetY) {
        const portalRadius = 30; // Define the radius for positioning
        this.ctx.fillStyle = 'silver'; // Optional: Set a color for the portal border

        for (const portal of this.portals) {
            const x = portal.position[0] - cameraOffsetX;
            const y = portal.position[1] - cameraOffsetY;

            // Draw the portal image instead of a circle
            this.ctx.drawImage(this.portalImage, x - portalRadius, y - portalRadius, portalRadius * 2, portalRadius * 2);
        }
    }
}