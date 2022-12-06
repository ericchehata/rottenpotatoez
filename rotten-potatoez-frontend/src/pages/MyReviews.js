import React, { useEffect } from "react";
import axios from "axios";
import { Box, Stack, Typography } from "@mui/material";
import Review from "../components/Review";

const MyReviews = () => {
  const [reviews, setReviews] = React.useState([]);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

  const loadData = async () => {
    if(!localStorage.getItem('username')) window.location.href = '/signin';
    const reviewsRes = await axios.get(
      `reviews/user/${localStorage.getItem("username")}`,
    );
    setReviews(reviewsRes.data);
  };

  return (
    <>
      {reviews.length ? (
        <Stack spacing={2} sx={{ pt: "3rem" }}>
          {reviews.map((review) => (
            <Review
              key={review.id}
              review={review}
              fromMyReviews={true}
              refresh={loadData}
            />
          ))}
        </Stack>
      ) : (
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            mt: "3rem",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            You have not written any reviews yet
          </Typography>
        </Box>
      )}
    </>
  );
};
export default MyReviews;
