from fastapi import APIRouter

router = APIRouter()

# Placeholder admin routes
@router.get('/')
def admin_root():
    return {"status": "admin endpoint placeholder"}
