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

import React, {Component} from 'react';
import {Dropdown} from "react-bootstrap";

interface EdgeListProps {
    makeRequestLong(start: string, end: string): any;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface EdgeListState {
    textInput: string;
    buildingNames: Array<any>;
    start: string;
    end: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            textInput: "",
            buildingNames: [],
            start: "",
            end:""
        }
    }

    handleChange = (event: any) => {
        this.setState({
            textInput: event.target.value
        })
    }

    handleClickForDraw = () => {
        this.props.makeRequestLong(this.state.start, this.state.end);
    }

    handleClickForClear = () => {
        this.props.makeRequestLong("", "");
        this.setState({start:"", end:""});
    }

    componentDidMount = async () => {  // <- Syntax for making async arrow functions.
        try {
            //fetch all building names from spark
            let responsePromise = fetch("http://localhost:4567/building-names");

            let response = await responsePromise;

            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //make building names from Json String to a Json object.
            let text = await response.json();
            let buildingList = [];
            //buildingName are the keys (i.e shortNames)
            for (var buildingName in text) {
                buildingList.push({
                    "shortName": buildingName,
                    "longName": text[buildingName]
                })
            }

            this.setState({
                buildingNames: buildingList
            });
            console.log(buildingList);

        } catch (e) {
            // If an error/exception happens (such as if the fetch URL is wrong or the
            // server is offline), then we'll end up here. Probably best to show a message to the
            // user.
            alert("There was an error contacting the server.");
            console.log(e);  // Logging the error can be nice for debugging.
        }
    }

    setStateForStart = (event: any) => {
        this.setState({
            start: event.target.name
        })
    }

    setStateForEnd = (event : any) => {
        this.setState({
            end: event.target.name
        })
    }



    render() {
        const dropDownStart = this.state.buildingNames.map((obj, index) => {
            return <Dropdown.Item key = {index} name = {obj.shortName} onClick = {this.setStateForStart}>
                {obj.longName}
            </Dropdown.Item>
        })
        const dropDownEnd = this.state.buildingNames.map((obj, index) => {
            return <Dropdown.Item key = {index} name = {obj.shortName} onClick = {this.setStateForEnd}>
                {obj.longName}
            </Dropdown.Item>
        })

        return (
            <div className="d-flex justify-content-center">
                <Dropdown>
                    <Dropdown.Toggle variant="btn btn-outline-dark" id="dropdown-basic-button" title='Choose first buildings'>
                        start: {this.state.start}
                    </Dropdown.Toggle>

                    <Dropdown.Menu className='scroll'>
                        {dropDownStart}
                    </Dropdown.Menu>
                </Dropdown>

                <Dropdown>
                    <Dropdown.Toggle variant="btn btn-outline-dark" id="dropdown-basic-button" title="Choose second buildings">
                        end: {this.state.end}
                    </Dropdown.Toggle>

                    <Dropdown.Menu className='scroll'>
                        {dropDownEnd}
                    </Dropdown.Menu>
                </Dropdown>
                <button onClick={this.handleClickForDraw}>Draw</button>
                <button onClick={this.handleClickForClear}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
