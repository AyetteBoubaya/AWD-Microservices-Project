from fastapi import FastAPI

app = FastAPI(
    title="Smart Irrigation AI",
    description="Microservice de prédiction intelligente des besoins en irrigation",
    version="1.0.0"
)

@app.get("/actuator/health", tags=["Health"])
def health_check():
    return {
        "service": "irrigation-ai-service",
        "status": "UP"
    }

@app.get("/", tags=["General"])
def root():
    return {
        "message": "Welcome to Smart Irrigation AI Service"
    }
