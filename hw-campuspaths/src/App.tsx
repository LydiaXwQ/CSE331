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

/**
 * Disclaimer: Please note that some extra front-end features in this homework (i.e drop down function and css modification)
 * was a collaborative effort between myself and another student.
 * While every effort was made to ensure the accuracy and completeness of the work presented,
 * any errors or omissions are the responsibility of the authors alone.
 * While the work is through collaboration, this meaning we used the same libraries(like dropdown) from
 * React bootstrap website. This doest not mean we copied each other's code directly for the basic functionalities
 * of this homework.
 */

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    requestResult: Array<any>
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            requestResult: []
        };
    }
        makeRequestLong = async (start: string, end: string) => {  // <- Syntax for making async arrow functions.

            if(start === "" || end === "") {
                this.setState({
                    requestResult: []
                })
                return;
            }

            try {
                // Note that we need the "http://" here for browser security reasons.
                // If you remove it, we'll get an error complaining about not having it.
                let responsePromise = fetch("http://localhost:4567/find-path?start=" + start + "&end=" + end);

                // responsePromise is a Promise<Response>. We can get the value out of the promise by waiting
                // until the promise resolves:
                let response = await responsePromise;

                // Now that we have a response, we should check the status code, make sure it's OK=200:
                if (!response.ok) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    return; // Don't keep trying to execute if the response is bad.
                }

                // Now we can request to get the data out of the response. We know that this
                // particular endpoint just returns text, so we'll use the .text() function
                // to ask for a promise that will give us the text from inside the response.
                // In your actual project, you'll probably use response.json() instead.
                let textPromise = response.json();

                // The text() function actually returns a Promise<string>, so we need to wait for
                // it to resolve before we can have the string.
                let text = await textPromise;
                // List of segments
                let paths = text.path;

                // Now that we have the string, let's stick it in state so it'll be displayed
                // to the user.
                this.setState({
                    requestResult: paths
                });
            } catch (e) {
                // If an error/exception happens (such as if the fetch URL is wrong or the
                // server is offline), then we'll end up here. Probably best to show a message to the
                // user.
                alert("There was an error contacting the server.");
                console.log(e);  // Logging the error can be nice for debugging.
            }

    }
    render() {
        return (
            <div className="page">
                <h1 className="app-title">Path finder!</h1>
                <div>
                    <Map parsedEdges={this.state.requestResult} />
                </div>
                <EdgeList
                    makeRequestLong={this.makeRequestLong}
                />
            </div>
        );
    }
}

export default App;
