const BASE_URL = 'http://localhost:8080/api/v1';

export const fetchAllPatients = async (token) => {
  try {
    console.log(token);
    const res = await fetch(`http://localhost:8080/api/v1/patients/list`, {
      headers: {
        Authorization: `Basic ${token}`,
      },
    });
    if (res.ok) {
      const result = await res.json();
      console.log(result);
      return result;
    } else {
      console.log(res.status, res.statusText);
      return [];
    }
  } catch (err) {
    console.log(err);
    return [];
  }
};

