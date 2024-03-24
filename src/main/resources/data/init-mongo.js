const fs = require('fs');

// Path to the JSON file
const jsonFilePath = './items.json';

// Function to read JSON file
const readJSONFile = (filePath) => {
    try {
        const jsonString = fs.readFileSync(filePath);
        return JSON.parse(jsonString);
    } catch (error) {
        console.error('Error reading JSON file:', error);
        process.exit(1);
    }
};

const jsonData = readJSONFile(jsonFilePath);

db = db.getSiblingDB('auctions');
db.createCollection('items');

db.items.insertMany(jsonData);
