import React from "react";
import styled from "styled-components";
import colorCodes from "../styles/color-codes";
import { IArtist, ITrackMusixMatchAPIParams } from "../../../shared";

const Wrapper = styled.div`
  width: 350px;
  background: ${colorCodes.deepMatteGrey};
  border-radius: 25px 25px 25px 25px;
  margin: 10px;
`;

const Title = styled.div`
  font-size: 20px;
  text-align: center;
  color: ${colorCodes.areYaYellow};
`;

const MetaData = styled.div`
  font-size: 15px;
  text-align: left;
  margin: 10px 50px;

  color: ${colorCodes.silverFox};
`;

const GetTracks = styled.div`
  font-size: 15px;
  margin: 10px 0;
  color: ${colorCodes.sandTanShadow};
  cursor: pointer;
`;

Wrapper.displayName = "Wrapper";
Title.displayName = "Title";
MetaData.displayName = "MetaData";
GetTracks.displayName = "GetTracks";

interface ArtistCardProps extends IArtist {
  getAllTracks: Function;
}

const ArtistCardComponent: React.FC<ArtistCardProps> = (
  props: ArtistCardProps
) => {
  return (
    <Wrapper>
      <Title>{props.name}</Title>
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div>
          <MetaData>Rating: {props.rating}</MetaData>
          {/* Country */}
          {props.country !== "" ? (
            <MetaData>Country: {props.country.toUpperCase()}</MetaData>
          ) : null}
          {/* Twitter URL */}
          {props.twitterURL !== "" ? (
            <MetaData>
              <a
                href={props.twitterURL}
                style={{
                  textDecoration: "none",
                  color: colorCodes.sandTanShadow
                }}
                target="_blank"
              >
                Twitter
              </a>
            </MetaData>
          ) : null}
        </div>
        <GetTracks
          onClick={() => {
            const params: ITrackMusixMatchAPIParams = {
              lyricsRequired: "1",
              page: 1,
              pageSize: 30,
              artistID: `${props.artistID}`,
              name: props.name
            };
            props.getAllTracks(params);
          }}
        >
          Get all tracks
        </GetTracks>
      </div>
    </Wrapper>
  );
};

export default ArtistCardComponent;