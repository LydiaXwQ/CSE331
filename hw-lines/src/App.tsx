/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    input: string
}


class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
      input: ""
    };
  }

  render() {

  function parseData(input : string) {
      const resultLines: Array<any> = [];
      if (input === "") {
        return resultLines;
      }
      //convert userInput into list of lines (exclude empty/space line), where one Array Element is one line.
      const listLines: Array<String> = input.trim().split('\n').filter(eachLine => eachLine.trim() !== "");
      for (let i = 0; i < listLines.length; i++) {
          //listLines[i] is the ith line in the listLines
          //convert the coords in a single line into a new array, where every element of "line"
          //is x1/y1/x2/y2/color accordingly.
          let line: Array<String> = listLines[i].split(" ");
          const x1 = Number(line[0]);
          const y1 = Number(line[1]);
          const x2 = Number(line[2]);
          const y2 = Number(line[3]);
          const lineIndex = i;
          if (isNaN(x1)) {
              alert("Invalid input of x1 at line" + lineIndex + ". x1 must be an integer.");
              return resultLines;
          } else if (isNaN(y1)) {
              alert("Invalid input of y1 at line" + lineIndex + ". y1 must be an integer.");
              return resultLines;
          } else if (isNaN(x2)) {
              alert("Invalid input of x2 at line" + lineIndex + ". x2 must be an integer.");
              return resultLines;
          } else if (isNaN(y2)) {
              alert("Invalid input of y2 at line" + lineIndex + ". y2 must be an integer.");
              return resultLines;
          }

          if (line.length != 5) {
              alert("Invalid line length. Every line in the text box should be " +
                  "in the format of x1 y1 x2 y2 color. Note: Each element should be separated by only one space!");
              return resultLines;
          }

          if (x1 > 4000 || x1 < 0) {
              alert("Input out of bounds at line" + lineIndex + ". x1 must be in range of 0-4000");
              return resultLines;
          } else if (y1 > 4000 || y1 < 0) {
              alert("Input out of bounds at line" + lineIndex + ". y1 must be in range of 0-4000");
              return resultLines;
          } else if (x2 > 4000 || x2 < 0) {
              alert("Input out of bounds at line" + lineIndex + ". x2 must be in range of 0-4000");
              return resultLines;
          } else if (y2 > 4000 || y2 < 0) {
              alert("Input out of bounds at line" + lineIndex + " y2 must be in range of 0-4000");
              return resultLines;
          }

          resultLines.push({
              x1: x1,
              y1: y1,
              x2: x2,
              y2: y2,
              color: line[4],
              key: lineIndex
          });
      }
      return resultLines;
  }

  const handleDraw = (userInput: string) => {
      this.setState({input: userInput});
  }

  const parsedEdges = parseData(this.state.input);


    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          <Map parsedEdges={parsedEdges} />
        </div>
        <EdgeList
          onChange={handleDraw}
        />
      </div>
    );
  }
}

export default App;
