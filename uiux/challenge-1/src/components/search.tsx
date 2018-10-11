import * as React from 'react';

import SearchBar from './search-bar';
import Artists from './artists';

export default class Search extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            artists: null
        };
    }

    setArtists(artists) {
        this.setState({
            artists: artists,
            searched: true
        });
    }

    render() {
        return (
            <div>
                <SearchBar setArtists={this.setArtists.bind(this)} />
                {
                    this.state['searched'] ?
                        this.state['artists'] && this.state['artists'].length > 0 ? <Artists artists={this.state['artists']} /> : 'No artist'
                        : ''
                }
            </div>
        )
    }
}