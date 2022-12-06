import React, { useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  Container,
  Link,
  Typography,
} from "@mui/material";
import MovieStyle from "../styles/MovieStyle";
import Genres from "../components/Genres";
import MovieRating from "../components/MovieRating";
import Review from "../components/Review";
import CreateReviewForm from "../components/CreateReviewForm";

const Movie = () => {
  const { id } = useParams();
  const [movie, setMovie] = React.useState(null);
  const [reviews, setReviews] = React.useState([]);
  const [user, setUser] = React.useState(null);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

  const loadData = async () => {
    const movieRes = await axios.get(`movies/${id}`);
    const reviewsRes = await axios.get(`reviews/movie/${id}`);
    setMovie(movieRes.data);
    setReviews(reviewsRes.data);
    if (localStorage.getItem("username")) {
      const userRes = await axios.get(
        `users/${localStorage.getItem("username")}`,
      );
      setUser(userRes.data);
    }
  };

  return (
    <>
      {movie && (
        <Card sx={MovieStyle.container}>
          <CardHeader
            title={
              <Container sx={MovieStyle.sectionContainer}>
                <Typography>{movie?.title}</Typography>
                <MovieRating reviews={reviews} />
              </Container>
            }
            subheader={
              <Container sx={MovieStyle.sectionContainer}>
                <span style={MovieStyle.subheader}>
                  {movie?.releaseDate.split("-")[0]}
                </span>
                <span style={MovieStyle.subheader}>{movie?.rating}</span>
                <span>{movie?.duration} min</span>
              </Container>
            }
          />
          <CardContent>
            <Container sx={MovieStyle.sectionContainer}>
              <Genres genres={movie?.genres} />
            </Container>
            {user ? (
              <CreateReviewForm user={user} movie={movie} refresh={loadData} />
            ) : (
              <Container sx={MovieStyle.siginLinkContainer}>
                <Link href="/signin" variant="body2">
                  {"Sign in to write a review"}
                </Link>
              </Container>
            )}
            {reviews.map((review) => (
              <Review key={review.id} review={review} />
            ))}
          </CardContent>
        </Card>
      )}
    </>
  );
};

export default Movie;
