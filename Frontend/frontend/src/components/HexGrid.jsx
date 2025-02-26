import React, { useState } from "react";
import "../styles/HexGrid.css";

const gridSize = 8; // 8x8 hex grid
const hexSize = 40; // Size of each hexagon
const hexWidth = 2 * hexSize;
const hexHeight = Math.sqrt(3) * hexSize;
const horizOffset = hexWidth * 0.75;
const vertOffset = hexHeight * 0.5;

const initialGreenHexes = ["0,0", "0,1", "0,2", "1,0", "1,1"]; // Player 1
const initialRedHexes = ["6,7", "6,6", "7,5", "7,6", "7,7"]; // Player 2

const HexGrid = () => {
    const [hexes, setHexes] = useState({});

    const handleHexClick = (row, col) => {
        const key = `${row},${col}`;
        setHexes((prev) => ({
            ...prev,
            [key]: prev[key] === "minion" ? null : "minion"
        }));
    };

    const renderHexes = () => {
        let hexElements = [];
        for (let row = 0; row < gridSize; row++) {
            for (let col = 0; col < gridSize; col++) {
                let x = col * horizOffset;
                let y = row * vertOffset * 2 + ((col+1) % 2) * vertOffset;
                let key = `${row},${col}`;

                let fillColor = "white";
                if (initialGreenHexes.includes(key)) fillColor = "lightgreen";
                else if (initialRedHexes.includes(key)) fillColor = "lightcoral";

                hexElements.push(
                    <polygon
                        key={key}
                        points="40,0 20,34.64 -20,34.64 -40,0 -20,-34.64 20,-34.64"
                        transform={`translate(${x},${y})`}
                        className="hex"
                        fill={fillColor}
                        stroke="black"
                        strokeWidth="2"
                        onClick={() => handleHexClick(row, col)}
                    />
                );
            }
        }
        return hexElements;
    };

    return (
        <svg
            viewBox={`-20 -${hexHeight + 40} ${gridSize * horizOffset + hexWidth * 0.25} 
            ${gridSize * vertOffset * 2.5 + hexHeight}`} width="100%" height="100vh">
            {renderHexes()}
        </svg>
    );
};

export default HexGrid;
