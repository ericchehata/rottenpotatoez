import React, { useEffect } from "react";
import axios from "axios";
import { Stack } from "@mui/material";
import Review from "../components/Review";

const MyReviews = () => {
  const [reviews, setReviews] = React.useState([]);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

  const loadData = async () => {
    const reviewsRes = await axios.get(`reviews/user/${localStorage.getItem('username')}`);
    setReviews(reviewsRes.data);
  };

  return (
    <Stack spacing={2} sx={{pt: "3rem"}}>
      {reviews.map((review) => (
        <Review key={review.id} review={review} fromMyReviews={true} refresh={loadData}/>
      ))}
    </Stack>
  );
};
export default MyReviews;
