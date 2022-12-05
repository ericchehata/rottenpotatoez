import React from "react";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  ListItem,
  Rating,
  Typography,
} from "@mui/material";

const Review = (props) => {

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
    </ListItem>
  );
};

export default Review;