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

interface EdgeListProps {
    onChange(edges: string): any;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface EdgeListState {
    textInput: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            textInput: ""
        }
    }

    handleChange = (event: any) => {
        this.setState({
            textInput: event.target.value
        })
    }

    handleClickForDraw = () => {
        this.props.onChange(this.state.textInput);
    }

    handleClickForClear = () => {
        this.props.onChange("");
        this.setState({textInput: ""});
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.handleChange}
                    value={this.state.textInput}
                    placeholder={"Type here..."}
                /> <br/>
                <button onClick={this.handleClickForDraw}>Draw</button>
                <button onClick={this.handleClickForClear}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
