var express = require('express');
var router = express.Router();
var axios = require('axios');
var bodyParser = require('body-parser');

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({extended: true}));

const API_BASE_URL = "http://localhost:8080/expense";
const API_URL_ADD = `${API_BASE_URL}/save`;
const API_URL_UPDATE = `${API_BASE_URL}/update`;
const API_URL_DELETE = `${API_BASE_URL}/delete`;
const API_URL_DISPLAY_ALL = `${API_BASE_URL}/list`;
const API_URL_DISPLAY_BY_ID = `${API_BASE_URL}/list`;

// Central error handler
const handleError = (err, res) => {
  console.error('API Error:', err.message);
  if (err.response) {
    console.error('API Response:', err.response.data);
  }
  res.status(err.status || 500).send(err.message || 'Error processing request');
};

// Display all expenses
router.get('/', async (req, res) => {
  try {
    const response = await axios.get(API_URL_DISPLAY_ALL);
    res.render('display', { expenses: response.data });
  } catch (err) {
    handleError(err, res);
  }
});


// Render add expense form
router.get('/add', (req, res) => {
  res.render('add'); // Assuming you have an add-form template
});

// Handle form submission to add expense
router.post('/submit', async (req, res) => {
  try {
    await axios.post(API_URL_ADD, req.body);
    res.redirect('/');
  } catch (err) {
    handleError(err, res);
  }
});

// Render edit form
router.get('/edit/:id', async (req, res) => {
  try {
    const response = await axios.get(`${API_URL_DISPLAY_BY_ID}/${req.params.id}`);
    res.render('edit', { expense: response.data });
  } catch (err) {
    handleError(err, res);
  }
});

// Handle form submission to update expense
router.post('/edit/:id', async (req, res) => {
  try {
    await axios.put(`${API_URL_UPDATE}/${req.params.id}`, req.body);
    res.redirect('/');
  } catch (err) {
    handleError(err, res);
  }
});

// Delete expense
router.get('/delete/:id', async (req, res, next) => {
  try {
    const id = req.params.id;
    if (!id || isNaN(id)) {
      const err = new Error('Invalid ID');
      err.status = 400;
      throw err;
    }
    await axios.delete(`${API_URL_DELETE}/${id}`);
    res.redirect('/');
  } catch (err) {
    handleError(err, res);
  }
});
// get by id
// Display expense by id
router.get('/:id', async (req, res) => {
  try {
    const response = await axios.get(`${API_URL_DISPLAY_BY_ID}/${req.params.id}`);
    res.render('displayid', { expense: response.data });
  } catch (err) {
    handleError(err, res);
  }
});

module.exports = router;