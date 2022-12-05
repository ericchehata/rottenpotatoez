import React from "react";
import axios from "axios";
import {
  Alert,
  Avatar,
  Box,
  Button,
  Container,
  CssBaseline,
  Rating,
  TextField,
  Typography,
} from "@mui/material";
import RateReviewIcon from '@mui/icons-material/RateReview';

const CreateReviewForm = (props) => {
  const [rating, setRating] = React.useState(props.review ? props.review.rating : 0);
  const [title, setTitle] = React.useState(props.review ? props.review.title : "");
  const [description, setDescription] = React.useState(props.review ? props.review.description : "");
  const [error, setError] = React.useState();

  const clearInputs = () => {
    setRating(0);
    setTitle("");
    setDescription("");
  };

  const addReview = () => {
    axios.post("reviews", {
      rating: rating,
      title: title,
      description: description,
      movie: props.movie,
      user: props.user
    }).then(() => {
      props.refresh();
      setError(null);
      clearInputs();
    }).catch((err) => {
      setError(err.response.data);
    });
  };

  const handleRatingChange = (event) => {
    setRating(+event.target.value);
  };

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
          <RateReviewIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          {props.review ? "Edit Review" : "Add Review"}
        </Typography>
        <Box sx={{ mt: 1 }}>
          <Rating name="half-rating" value={rating} onChange={handleRatingChange}/>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Title"
            type="title"
            id="title"
            value={title}
            onChange={handleTitleChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            multiline
            name="description"
            label="Description"
            type="description"
            id="description"
            value={description}
            onChange={handleDescriptionChange}
          />
          {props.review 
          ? 
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={!rating || !title || !description}
            onClick={() => props.editReview(title, description, rating)}
          >
            Edit Review
          </Button>
          :
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={!rating || !title || !description}
            onClick={addReview}
          >
            Add Review
          </Button>}
          {error && (
            <Alert severity="error" sx={{ mt: 2 }}>
              {error}
            </Alert>
          )}
        </Box>
      </Box>
    </Container>
  );
};

export default CreateReviewForm;
