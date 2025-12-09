# Monsieur Cuisine Connect - Dokumentacja API

Dokumentacja endpointów API dla aplikacji Monsieur Cuisine Connect (MCC) (Lidlomix).

## Bazowe URL

### API główne
- **Endpoint**: `https://mc20.monsieur-cuisine.com/mcc/api/v1/`
- **API wiadomości**: `https://api2.monsieur-cuisine.com/mcc/api/v1/`

### Aktualizacje aplikacji

#### Wersje 16-18
- **Lista wersji**: `https://mc20.monsieur-cuisine.com/666a60bc-0ce2-4878-9e3b-23ba3ceaba5a/versions.txt`
- **Pobieranie**: `https://mc20.monsieur-cuisine.com/666a60bc-0ce2-4878-9e3b-23ba3ceaba5a/{version_name}`

#### Wersje 22-28
- **Lista wersji**: `https://update22.simplexion.pm/4b606313-3631-4d5f-a856-ca0edecf0c13/versions.txt`
- **Pobieranie**: `https://update22.simplexion.pm/4b606313-3631-4d5f-a856-ca0edecf0c13/{version_name}`

---

## Endpointy API

### Uwierzytelnianie i rejestracja

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/authenticate`
Uwierzytelnia użytkownika i zwraca token.

**Body (JSON):**
```json
{
  "username": "string",
  "password": "string"
}
```

**Headers:**
- `Accept-Language: {language_code}`

**Response (200):**
```json
{
  "token": "string"
}
```

---

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/register`
Rejestruje nowego użytkownika.

**Body (JSON):**
```json
{
  "uid": "string",
  "displayname": "string",
  "password": "string"
}
```

**Headers:**
- `Accept-Language: {language_code}`

**Response:**
- `200` - Rejestracja pomyślna
- `409` - Użytkownik już istnieje (zwraca `ConstraintResponse`)

---

### Dane użytkownika

#### GET `https://mc20.monsieur-cuisine.com/mcc/api/v1/userdata`
Pobiera dane użytkownika.

**Headers:**
- `Authorization: {user_token}`

**Response (200):** `UserDataResponse`

---

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/privacyterms`
Aktualizuje ustawienia prywatności użytkownika.

**Headers:**
- `Authorization: {user_token}`

**Body (JSON):**
```json
{
  "terms": {
    "newsletter": boolean,
    "data": boolean,
    "version": integer,
    "version_terms": integer
  }
}
```

---

### Przepisy

#### GET `https://mc20.monsieur-cuisine.com/mcc/api/v1/recipe/all`
Pobiera wszystkie przepisy.

**Headers:**
- `X-Recipe-Type: {recipe_type}`
- `Accept-Language: {language_code}`
- `Authorization: {user_token}` (opcjonalnie)

**Query Parameters:**
- `since` - Data ISO (opcjonalnie) - pobiera przepisy od określonej daty

**Response (200):** Lista obiektów `RecipeResponse`

---

#### GET `https://mc20.monsieur-cuisine.com/mcc/api/v1/recipe/ids`
Pobiera listę ID wszystkich przepisów.

**Headers:**
- `X-Recipe-Type: {recipe_type}`
- `Accept-Language: {language_code}`

**Response (200):**
```json
{
  "ids": [123, 456, 789]
}
```

---

### Ulubione

#### GET `https://mc20.monsieur-cuisine.com/mcc/api/v1/favorite`
Pobiera listę ID ulubionych przepisów użytkownika.

**Headers:**
- `Authorization: {user_token}`

**Query Parameters:**
- `timeout` - Timeout w ms (opcjonalnie)

**Response (200):**
```json
{
  "ids": [123, 456, 789]
}
```

---

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/favorite/{recipe_id}`
Dodaje przepis do ulubionych.

**Headers:**
- `Authorization: {user_token}`

**URL Parameters:**
- `recipe_id` - ID przepisu

**Body:** `""` (pusty string)

---

#### DELETE `https://mc20.monsieur-cuisine.com/mcc/api/v1/favorite/{recipe_id}`
Usuwa przepis z ulubionych.

**Headers:**
- `Authorization: {user_token}`

**URL Parameters:**
- `recipe_id` - ID przepisu

---

### Konfiguracja urządzenia

#### GET `https://mc20.monsieur-cuisine.com/mcc/api/v1/machineconfig/{machine_id}`
Pobiera konfigurację urządzenia.

**URL Parameters:**
- `machine_id` - Identyfikator urządzenia SE (format xxxxxxxxxxxxxxxx-xxxx)

**Response:**
- `200` - `MachineConfigResponse`
- `404` - Konfiguracja nie znaleziona

---

### Statystyki użytkowania

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/machine/`
Wysyła informacje o urządzeniu (log użytkowania).

**Headers:**
- `Accept-Language: {language_code}`

**Body (JSON):** Obiekt `MachineInfoData` zawierający wszystkie informacje o urządzeniu

---

#### POST `https://mc20.monsieur-cuisine.com/mcc/api/v1/event/`
Wysyła zdarzenie użytkowania urządzenia.

**Headers:**
- `Accept-Language: {language_code}`

**Body (JSON):**
```json
{
  "seserial": "string",
  "data": {
    "event": "event_type",
    "payload": {}
  }
}
```

**Typy zdarzeń:**
- `START_MANUAL_COOKING` - rozpoczęcie gotowania manualnego
  ```json
  {
    "time": integer,
    "speed": integer,
    "temperature": integer
  }
  ```
- `STOP_MANUAL_COOKING` - zakończenie gotowania manualnego
- `START_PRESET_COOKING` - rozpoczęcie gotowania preset
  ```json
  {
    "preset": "STEAMING|ROASTING|KNEADING",
    "time": integer,
    "speed": integer,
    "temperature": integer
  }
  ```
- `STOP_PRESET_COOKING` - zakończenie gotowania preset
- `START_RECIPE_STEP_COOKING` - rozpoczęcie kroku przepisu
  ```json
  {
    "recipeId": long,
    "stepIndex": integer
  }
  ```
- `STOP_RECIPE_STEP_COOKING` - zakończenie kroku przepisu
- `RECIPE_DONE` - ukończenie przepisu
  ```json
  {
    "recipeId": long
  }
  ```
- `PAUSE_STANDBY` - przejście w tryb czuwania
- `PAUSE_ONLINE` - powrót z trybu czuwania
- `CONNECTED_TO_SERVER` - sprawdzenie połączenia z serwerem
- `SPECIAL_SE` - specjalne zdarzenie numeru SE

---

### Wiadomości kampanii

#### GET `https://api2.monsieur-cuisine.com/mcc/api/v1/message`
Pobiera wiadomość kampanii.

**Response (200):** Obiekt `CampaignMessage`

---

#### DELETE `https://api2.monsieur-cuisine.com/mcc/api/v1/message`
Usuwa wiadomość kampanii.

---

### Dokumenty HTML

#### GET `https://mc20.monsieur-cuisine.com/mcc_privacy.{language}.html`
Pobiera politykę prywatności w formacie HTML.

**URL Parameters:**
- `language` - Kod języka (np. `pl`, `en`, `de`)

**Response (200):** HTML jako tekst

---

#### GET `https://mc20.monsieur-cuisine.com/gen_terms.{language}.html`
Pobiera warunki użytkowania w formacie HTML.

**URL Parameters:**
- `language` - Kod języka (np. `pl`, `en`, `de`)

**Response (200):** HTML jako tekst

---

## Uwagi

- Większość endpointów wymaga tokenu uwierzytelniającego uzyskanego z `/authenticate`
- Kody języków: `pl`, `en`, `de`, `fr`, itp.
- Wszystkie endpointy zwracają standardowe kody statusu HTTP (200, 404, 409, 500, etc.)
- Token użytkownika jest przechowywany lokalnie po udanym uwierzytelnieniu

---

