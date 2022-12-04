import * as React from "react";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";

const Genres = (props) => {
  return (
    <Stack direction="row" spacing={1}>
      {props.genres.map((genre) => (
        <Chip key={genre} label={genre} />
      ))}
    </Stack>
  );
};

export default Genres;
