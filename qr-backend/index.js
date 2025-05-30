import express from 'express';
import crypto from 'crypto';

const app = express();
const port = process.env.PORT || 3000;
app.use(express.json());

const SEED_EXPIRATION_MS = 5 * 60 * 1000; // 5 minutes
const seeds = new Map();

setInterval(() => {
  const now = Date.now();
  for (const [key, expiresAt] of seeds.entries()) {
    if (expiresAt <= now) {
      seeds.delete(key);
    }
  }
}, 60 * 1000);

function generateSeed() {
  return crypto.randomBytes(16).toString('hex');
}

// Endpoint to create a new seed
app.post('/seed', (req, res) => {
  const value = generateSeed();
  const expiresAt = Date.now() + SEED_EXPIRATION_MS;
  seeds.set(value, expiresAt);
  res.json({ seed: value, expires_at: new Date(expiresAt).toISOString() });
});

// Endpoint to validate a seed
app.post('/validate', (req, res) => {
  const { seed } = req.body;
  const expiration = seeds.get(seed);

  if (!expiration) {
    return res.status(404).json({ valid: false, reason: 'Seed not found' });
  }

  if (Date.now() >= expiration) {
    return res.status(400).json({ valid: false, reason: 'Seed expired' });
  }

  res.json({ valid: true });
});


// ----- START

app.listen(port, '0.0.0.0', () => {
  if (process.env.NODE_ENV !== 'production') {
    console.log(`Server running at http://localhost:${port}`);
  }
});
