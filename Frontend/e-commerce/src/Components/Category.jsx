const Category = (props) => {
  const { id, name, image } = props.data;
  return (
    <div class="col-sm-3">
      <div class="card">
        <img src={image} class="card-img-top" alt="..." />
        <div class="card-body">
          <h5 class="card-title">{name}</h5>

          <a href="#" class="btn btn-primary btn-block">
            Select
          </a>
        </div>
      </div>
    </div>
  );
};
export default Category;
