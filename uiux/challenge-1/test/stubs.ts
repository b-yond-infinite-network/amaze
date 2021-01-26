import { Artist, parseRating } from "../app/models"

export function createArtist(): Artist {
  return {
    id: Math.floor(Math.random() * 10000),
    name: "Pantera",
    rating: parseRating(Math.floor(Math.random() * 10), 10),
  }
}

export function createRandomAmountOfArtists(): Artist[] {
  return Array.from({ length: Math.floor(Math.random() * 100) }, () =>
    createArtist()
  )
}
