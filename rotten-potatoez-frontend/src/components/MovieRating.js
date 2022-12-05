import React, { useEffect } from "react";
import { Rating, Typography } from "@mui/material";

const MovieRating = (props) => {
  const [rating, setRating] = React.useState(0);

  useEffect(() => {
    if (props.reviews.length) {
      let rating = 0;
      props.reviews.forEach((review) => {
        rating += review.rating;
      });
      setRating(rating / props.reviews.length);
    }
  }, [props.reviews]);

  return (
    <>
      {props.reviews.length === 0 ? (
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ marginLeft: "3%" }}
        >
          No reviews yet
        </Typography>
      ) : (
        <>
          {!props.fromTable && (
            <Typography
              sx={{ marginLeft: "3%", marginRight: "5px" }}
              color="text.secondary"
            >
              {rating}
            </Typography>
          )}
          <Rating name="read-only" value={rating} readOnly />
          {!props.fromTable && (
            <Typography sx={{ marginLeft: "5px" }} color="text.secondary">
              ({props.reviews.length})
            </Typography>
          )}
        </>
      )}
    </>
  );
};
export default MovieRating;
