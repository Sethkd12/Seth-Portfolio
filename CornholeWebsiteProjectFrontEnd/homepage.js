function goToProfile() {
  const params = new URLSearchParams(window.location.search);
  const userId = params.get('id');
  if (!userId) {
    alert("User ID not found in URL. Are you sure you're logged in?");
    return;
  }
  window.location.href = `profile.html?id=${userId}`;
}

// Fetches and displays every match on the homepage
async function loadAllMatches() {
  try {
    const resp = await fetch(`${baseUrl}/matches`);
    console.log("fetch /matches response:", resp);
    if (!resp.ok) {
      const body = await resp.text();
      console.error(`⚠️ Non‑200 status ${resp.status}:`, body);
      throw new Error(`Server returned ${resp.status}`);
    }
    const matches = await resp.json();
    console.log("Parsed JSON:", matches);

    const container = document.getElementById("matches-container");
    container.innerHTML = "";
    if (matches.length === 0) {
      container.innerHTML = "<p>No matches posted yet.</p>";
      return;
    }
    // … your rendering code here …
  } catch (err) {
    console.error("loadAllMatches error:", err);
    alert("Unable to load matches right now: " + err.message);
  }
}

document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("profile-button")
    .addEventListener("click", goToProfile);

  // Once the DOM is ready, load every match
  loadAllMatches();
});