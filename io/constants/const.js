const zones = [
    {
        id: 'common',
        bounds: { x: [0, 2125], y: [0, 2000] },
        portal1: { position: [2075, 1000], linkedZone: 'unusual' },
        portal2: { position: [2550, 1000], linkedZone: 'common' }
    },
    {
        id: 'unusual',
        bounds: { x: [2500, 4625], y: [0, 2000] },
        portal1: { position: [4575, 1000], linkedZone: 'rare' },
        portal2: { position: [5050, 1000], linkedZone: 'unusual' }
    },
    {
        id: 'rare',
        bounds: { x: [4625, 7125], y: [0, 2000] },
        portal1: { position: [7075, 1000], linkedZone: 'epic' },
        portal2: { position: [7550, 1000], linkedZone: 'rare' }
    },
    {
        id: 'epic',
        bounds: { x: [7125, 9625], y: [0, 2000] },
        portal1: { position: [9575, 1000], linkedZone: 'legendary' },
        portal2: { position: [10050, 1000], linkedZone: 'epic' }
    },
    {
        id: 'legendary',
        bounds: { x: [9625, 12125], y: [0, 2000] },
        portal1: { position: [12075, 1000], linkedZone: 'mythic' },
        portal2: { position: [12550, 1000], linkedZone: 'legendary' }
    },
    {
        id: 'mythic',
        bounds: { x: [12125, 14625], y: [0, 2000] },
        portal1: { position: [14575, 1000], linkedZone: 'ultra' },
        portal2: { position: [15050, 1000], linkedZone: 'mythic' }
    },
    {
        id: 'ultra',
        bounds: { x: [14625, 17125], y: [0, 2000] },
        portal1: { position: [17075, 1000], linkedZone: '???' },
        portal2: { position: [17550, 1000], linkedZone: 'ultra' }
    },
    {
        id: '???',
        bounds: { x: [17500, 21500], y: [0, 2000] },
        portal1: null,
        portal2: null
    } // Last zone with no portals
];

// Define walls between zones
const walls = [
    { bounds: { x: [2125, 2500], y: [0, 2000] } }, // Between common and unusual
    { bounds: { x: [4625, 5000], y: [0, 2000] } }, // Between unusual and rare
    { bounds: { x: [7125, 7500], y: [0, 2000] } }, // Between rare and epic
    { bounds: { x: [9625, 10000], y: [0, 2000] } }, // Between epic and legendary
    { bounds: { x: [12125, 12500], y: [0, 2000] } }, // Between legendary and mythic
    { bounds: { x: [14625, 15000], y: [0, 2000] } }, // Between mythic and ultra
    { bounds: { x: [17125, 17500], y: [0, 2000] } }  // Between ultra and ??? (last zone)
];

const portals = [
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

module.exports = { zones, walls, portals };