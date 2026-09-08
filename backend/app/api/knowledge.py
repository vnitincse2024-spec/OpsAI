from fastapi import APIRouter

router = APIRouter()

# Placeholder endpoints for knowledge base
@router.get('/')
def list_articles():
    return [{"title": "Database Connection Pool Exhaustion", "summary": "Root cause and mitigation"}]
