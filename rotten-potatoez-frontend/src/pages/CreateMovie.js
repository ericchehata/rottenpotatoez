import React from "react";
import axios from "axios";
import {
  Alert,
  Autocomplete,
  Avatar,
  Box,
  Button,
  Chip,
  Container,
  CssBaseline,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import MovieIcon from "@mui/icons-material/Movie";
import { FileUploader } from "react-drag-drop-files";
import { CircleSpinnerOverlay } from "react-spinner-overlay";
import genres from "../constants/genres";

const CreateMovie = () => {
  const [title, setTitle] = React.useState();
  const [releaseDate, setReleaseDate] = React.useState(null);
  const [duration, setDuration] = React.useState();
  const [rating, setRating] = React.useState("");
  const [selectedGenres, setSelectedGenres] = React.useState([]);
  const [genresSwitch, setGenresSwitch] = React.useState(false);
  const [file, setFile] = React.useState();
  const [error, setError] = React.useState();
  const [success, setSuccess] = React.useState();
  const [importError, setImportError] = React.useState();
  const [importSuccess, setImportSuccess] = React.useState();
  const [loading, setLoading] = React.useState(false);

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleReleaseDateChange = (newValue) => {
    setReleaseDate(newValue);
  };

  const handleDurationChange = (event) => {
    setDuration(event.target.value);
  };

  const handleRatingChange = (event) => {
    setRating(event.target.value);
  };

  const addGenre = (event, value) => {
    if (selectedGenres.length < 3 && value) {
      !selectedGenres.includes(value) && selectedGenres.push(value);
      setSelectedGenres(selectedGenres);
      setError(null);
    } else {
      value && setError("You can only select up to 3 genres");
    }
    setGenresSwitch(!genresSwitch);
  };

  const removeGenre = (genre) => {
    const index = selectedGenres.indexOf(genre);
    if (index > -1) {
      selectedGenres.splice(index, 1);
    }
    setSelectedGenres(selectedGenres);
    setError(null);
    setGenresSwitch(!genresSwitch);
  };

  const handleFileChange = (file) => {
    setFile(file);
  };

  const createMovie = () => {
    axios
      .post("movies", {
        title: title,
        releaseDate: releaseDate,
        duration: duration,
        rating: rating,
        genres: selectedGenres,
        picture: "picture.png",
      })
      .then(() => {
        setError(null);
        clearInputs();
        setSuccess("Movie created successfully");
      })
      .catch((err) => {
        setSuccess(null);
        setError(err.response.data);
      });
  };

  const importMovies = () => {
    setLoading(true);
    var formData = new FormData();
    formData.append("file", file);
    axios
      .post("movies/import", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })

      .then(() => {
        setLoading(false);
        setImportError(null);
        setImportSuccess("Movies imported successfully");
      })
      .catch((err) => {
        setLoading(false);
        setImportSuccess(null);
        setImportError(err.response.data);
      });
  };

  const clearInputs = () => {
    setTitle("");
    setReleaseDate(null);
    setDuration("");
    setRating("");
    setSelectedGenres([]);
    setGenresSwitch(!genresSwitch);
  };

  return (
    <Container component="main" maxWidth="xs" sx={{ pb: "5%" }}>
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary" }}>
          <MovieIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Add Movie
        </Typography>
        <Box sx={{ mt: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                name="title"
                required
                fullWidth
                id="title"
                label="Title"
                autoFocus
                value={title}
                onChange={handleTitleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth>
                <InputLabel id="rating-label">Rating</InputLabel>
                <Select
                  labelId="rating-label"
                  id="rating"
                  label="Rating"
                  value={rating}
                  onChange={handleRatingChange}
                >
                  <MenuItem value={""} disabled>
                    Select Rating
                  </MenuItem>
                  <MenuItem value={"G"}>G</MenuItem>
                  <MenuItem value={"PG"}>PG</MenuItem>
                  <MenuItem value={"PG13"}>PG-13</MenuItem>
                  <MenuItem value={"R"}>R</MenuItem>
                  <MenuItem value={"NC17"}>NC-17</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="duration"
                label="Duration"
                name="duration"
                type="number"
                value={duration}
                onChange={handleDurationChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                <DatePicker
                  required
                  fullWidth
                  id="releaseDate"
                  label="Release Date"
                  name="releaseDate"
                  value={releaseDate}
                  onChange={handleReleaseDateChange}
                  renderInput={(params) => <TextField {...params} />}
                />
              </LocalizationProvider>
            </Grid>
            <Grid item xs={12}>
              <Autocomplete
                fullWidth
                required
                disablePortal
                id="search-genres"
                options={genres}
                onChange={addGenre}
                renderInput={(params) => (
                  <TextField {...params} label="Select genres" />
                )}
              />
            </Grid>
            <Grid item xs={12}>
              <Stack direction="row" spacing={1}>
                {selectedGenres.map((genre) => (
                  <Chip
                    key={genre}
                    label={genre}
                    onDelete={() => removeGenre(genre)}
                  />
                ))}
              </Stack>
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={
              !title ||
              !releaseDate ||
              !duration ||
              !rating ||
              !selectedGenres.length
            }
            onClick={createMovie}
          >
            Add Movie
          </Button>
          {error && (
            <Alert severity="error" sx={{ mt: 2 }}>
              {error}
            </Alert>
          )}
          {success && (
            <Alert severity="success" sx={{ mt: 2 }}>
              {success}
            </Alert>
          )}
        </Box>
      </Box>
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <FileUploader
          handleChange={handleFileChange}
          name="file"
          types={["xlsx"]}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          disabled={!file}
          onClick={importMovies}
        >
          Import Movies
        </Button>
        {importError && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {importError}
          </Alert>
        )}
        {importSuccess && (
          <Alert severity="success" sx={{ mt: 2 }}>
            {importSuccess}
          </Alert>
        )}
      </Box>
      <CircleSpinnerOverlay loading={loading} overlayColor="rgba(0,0,0,0.2)" />
    </Container>
  );
};

export default CreateMovie;
