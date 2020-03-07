import axios from "axios";
import { Request, Response } from "express";
import { HTTP500Error } from "../../utils/httpErrors";

import { lyricsAPIBuilder } from "../../utils";
import { ILyrics, ILyricsMusixMatchAPIParams } from "../../../../shared";

const lyricsController = async (req: Request, res: Response) => {
  const lyricsAPIParams: ILyricsMusixMatchAPIParams = {
    trackID: req.params.id
  };
  const lyricsAPIURL = lyricsAPIBuilder(lyricsAPIParams);
  try {
    const response = await axios.get(lyricsAPIURL);
    if (!response.data.message.body.lyrics) {
      const error = new HTTP500Error({
        message: "Third party servers are not responding"
      });
      throw error;
    }

    const lyricsObject = response.data.message.body.lyrics;

    let result: ILyrics = {
      language: lyricsObject.lyrics_language,
      lyricsContent: lyricsObject.lyrics_body
    };
    // console.log(result);
    res.status(200).send(result);
  } catch (err) {
    const error = new HTTP500Error({
      message: "Internal server error"
    });
    throw error;
  }
};

export default lyricsController;
