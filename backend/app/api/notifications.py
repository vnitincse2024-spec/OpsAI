from fastapi import APIRouter

router = APIRouter()

# Placeholder notifications endpoint
@router.get('/')
def list_notifications():
    return [{"id": 1, "message": "Test notification", "type": "info"}]
