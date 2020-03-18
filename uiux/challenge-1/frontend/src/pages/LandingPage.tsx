import React from "react";
import styled from "styled-components";
import BounceLoader from "react-spinners/BounceLoader";
import { Pagination } from "antd";

import colorCodes from "../styles/color-codes";
import Header from "../components/Header";
import SearchComponent from "../components/Search";
import SearchResultBanner from "../components/SearchResultBanner";
import ArtistCardComponent from "../components/ArtistCard";
import SortFunctions from "../util/sort";

import { searchForArtists } from "../api/artist";
import { searchForTracks } from "../api/track";
import {
  IArtist,
  ITrack,
  IArtistMusixMatchAPIParams,
  ITrackMusixMatchAPIParams
} from "../../../shared";
import TrackCardComponent from "../components/TrackCard";

const Container = styled.div`
  width: 100%;
  max-height: 100vh;
  font-family: BitterItalic;
  font-size: 20px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
`;

const SearchResultWrapper = styled.div`
  overflow: scroll;
  width: 90%;
  margin: 20px auto;
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  flex-wrap: wrap;
  justify-content: space-around;
`;

const PaginationWrapper = styled.div`
  height: 50px;
  width: 100%;
  margin-bottom: 10px;
`;

Container.displayName = "Container";
SearchResultWrapper.displayName = "SearchResultWrapper";
PaginationWrapper.displayName = "PaginationWrapper";

export interface LandingPageState {
  sort: {
    type: string;
  };
  searchResult: {
    currentSearchName: string;
    totalAvailable: number;
    type: string;
    currentSearchParam:
      | IArtistMusixMatchAPIParams
      | ITrackMusixMatchAPIParams
      | {};
    paginatedResults: { [pageNumber: number]: IArtist[] | ITrack[] | [] };
    currentPage: number;
  };
  isLoading?: boolean;
  backendError: boolean;
}

class LandingPage extends React.Component<{}, LandingPageState> {
  constructor(props) {
    super(props);
    this.handleSearchRequest = this.handleSearchRequest.bind(this);
    this.sortResults = this.sortResults.bind(this);
    this.handlePageClick = this.handlePageClick.bind(this);
    this.getSearchResultComponents = this.getSearchResultComponents.bind(this);
    this.searchTracksByArtistID = this.searchTracksByArtistID.bind(this);
  }

  state: LandingPageState = {
    sort: {
      type: "NO_SORT"
    },
    searchResult: {
      currentSearchName: "",
      totalAvailable: 0,
      type: "artist",
      currentSearchParam: {},
      paginatedResults: { 1: [] },
      currentPage: 1
    },
    isLoading: false,
    backendError: false
  };

  async handleSearchRequest(type, params) {
    // Update state searchResult.type and result
    await this.setState({
      isLoading: true
    });
    let result: IArtist[] | ITrack[];
    try {
      if (type === "artist") {
        result = (await searchForArtists(params)) as IArtist[];
      } else {
        result = (await searchForTracks(params)) as ITrack[];
      }
    } catch (error) {
      await this.setState({
        ...this.state,
        isLoading: false,
        backendError: true
      });
      return;
    }

    // Sorting the result
    if (this.state.sort.type !== "NO_SORT") {
      result.sort(SortFunctions[this.state.sort.type]);
    }

    this.setState({
      ...this.state,
      searchResult: {
        currentSearchName: params.name,
        type: type,
        totalAvailable: result.length > 0 ? result[0].totalAvailable / 10 : 0,
        currentSearchParam: params,
        paginatedResults: {
          ...this.state.searchResult.paginatedResults,
          [params.page]: result
        },
        currentPage: parseInt(params.page)
      },
      isLoading: false,
      backendError: false
    });
  }

  async searchTracksByArtistID(params: ITrackMusixMatchAPIParams) {
    await this.handleSearchRequest("track", params);
    await this.setState({
      searchResult: {
        ...this.state.searchResult,
        currentSearchName: `get all tracks by ${params.name}`
      }
    });
  }

  getSearchResultComponents() {
    let results;
    if (this.state.searchResult.type === "artist") {
      results = this.state.searchResult.paginatedResults[
        this.state.searchResult.currentPage
      ] as IArtist[];
      return results.map((eachResult: IArtist) => (
        <ArtistCardComponent
          name={eachResult.name}
          artistID={eachResult.artistID}
          country={eachResult.country}
          rating={eachResult.rating}
          twitterURL={eachResult.twitterURL}
          totalAvailable={eachResult.totalAvailable}
          key={eachResult.artistID}
          getAllTracks={this.searchTracksByArtistID}
        />
      ));
    } else {
      results = this.state.searchResult.paginatedResults[
        this.state.searchResult.currentPage
      ] as ITrack[];
      return results.map((eachResult: ITrack) => (
        <TrackCardComponent
          artistName={eachResult.artistName}
          name={eachResult.name}
          explicit={eachResult.explicit}
          trackID={eachResult.trackID}
          hasLyrics={eachResult.hasLyrics}
          rating={eachResult.rating}
          numFavorite={eachResult.numFavorite}
          totalAvailable={eachResult.totalAvailable}
          key={eachResult.trackID}
        />
      ));
    }
  }

  async handlePageClick(page, pageSize) {
    if (this.state.searchResult.paginatedResults[page] !== undefined) {
      // If we already have the result in the paginatedResults prop, assign that
      // to the displaying result.
      const sortedPage = this.state.searchResult.paginatedResults[page].sort(
        SortFunctions[this.state.sort.type]
      );

      await this.setState({
        ...this.state,
        searchResult: {
          ...this.state.searchResult,
          currentPage: page,
          paginatedResults: {
            ...this.state.searchResult.paginatedResults,
            [page]: sortedPage
          }
        }
      });
    } else {
      // Else, query backend for that.
      const params = {
        ...this.state.searchResult.currentSearchParam,
        page: page
      };
      this.handleSearchRequest(this.state.searchResult.type, params);
    }
  }

  async sortResults(sortBy) {
    await this.setState({
      ...this.state,
      sort: {
        type: sortBy
      }
    });
    // Sort the current displaying page results.
    await this.handlePageClick(this.state.searchResult.currentPage, 0);
  }

  render() {
    return (
      <Container>
        <Header enableHomeButtonLink={false}></Header>
        <SearchComponent
          searchTriggered={this.handleSearchRequest}
          setSortType={this.sortResults}
        />
        <SearchResultBanner
          banner={
            this.state.searchResult.currentSearchName !== ""
              ? `Search results for ${this.state.searchResult.currentSearchName}`
              : ""
          }
        />
        {/* Show results */}
        <SearchResultWrapper>
          {this.state.isLoading ? (
            <div style={{ margin: "auto 0" }}>
              <BounceLoader
                size={50}
                color={colorCodes.areYaYellow}
                loading={true}
              />
            </div>
          ) : this.state.backendError ? (
            `Backend is not responding. Please check if the backend server is up and running.`
          ) : this.state.searchResult.currentSearchName !== "" &&
            this.state.searchResult.paginatedResults[
              this.state.searchResult.currentPage
            ].length === 0 ? (
            `¯\\_(ツ)_/¯`
          ) : (
            this.getSearchResultComponents()
          )}
        </SearchResultWrapper>
        {/* Show pagination */}
        <PaginationWrapper
          style={{
            fontFamily: "BitterRegular",
            textAlign: "center"
          }}
        >
          <Pagination
            size="medium"
            defaultCurrent={1}
            current={this.state.searchResult.currentPage}
            total={this.state.searchResult.totalAvailable}
            onChange={this.handlePageClick}
          />
        </PaginationWrapper>
      </Container>
    );
  }
}

export default LandingPage;
