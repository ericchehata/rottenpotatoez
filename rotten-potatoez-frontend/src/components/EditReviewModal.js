import { Card, Modal } from "@mui/material";
import CreateReviewForm from "./CreateReviewForm";
import ModalStyle from "../styles/ModalStyle";

const EditReviewModal = (props) => {
  return (
    <Modal
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Card sx={ModalStyle}>
        <CreateReviewForm
          movie={props.review.movie}
          user={props.review.user}
          review={props.review}
          editReview={props.editReview}
        />
      </Card>
    </Modal>
  );
};

export default EditReviewModal;
