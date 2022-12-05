import React from "react";
import axios from "axios";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  IconButton,
  ListItem,
  Rating,
  Typography,
} from "@mui/material";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import EditReviewModal from "./EditReviewModal";

const Review = (props) => {
  const [reviewOpen, setReviewOpen] = React.useState(false);

  const handleDelete = async () => {
    await axios.delete(`reviews/${props.review.id}`);
    props.refresh();
  };

  const handleReviewOpen = () => {
    setReviewOpen(true);
  };

  const handleReviewClose = () => setReviewOpen(false);

  const editReview = async (title, description, rating) => {
    console.log(title, description, rating);
    await axios.patch(`reviews`, {
      title: title,
      description: description,
      rating: rating,
      id: props.review.id,
      user: props.review.user,
      movie: props.review.movie,
    });
    props.refresh();
    handleReviewClose();
  };

  return (
    <ListItem alignItems="flex-start">
      <Card sx={{ width: "100%" }}>
        <CardHeader
          title={
            <Box
              sx={{
                display: "flex",
              }}
            >
              <Typography
                mr={"5%"}
              >{`${props.review.user.firstName} ${props.review.user.lastName}`}</Typography>
              <Rating value={props.review.rating} readOnly />
            </Box>
          }
          action={
            props.fromMyReviews && (
              <>
                <IconButton
                  size="large"
                  color="inherit"
                  onClick={handleReviewOpen}
                >
                  <EditIcon />
                </IconButton>
                <IconButton size="large" color="inherit" onClick={handleDelete}>
                  <DeleteIcon />
                </IconButton>
              </>
            )
          }
        ></CardHeader>
        <CardContent>
          <Typography gutterBottom component="div">
            {props.review.title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {props.review.description}
          </Typography>
        </CardContent>
      </Card>
      <EditReviewModal
        open={reviewOpen}
        handleClose={handleReviewClose}
        editReview={editReview}
        review={props.review}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      ></EditReviewModal>
    </ListItem>
  );
};

export default Review;
