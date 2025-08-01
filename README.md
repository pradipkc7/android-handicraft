# Crafted - Handmade Marketplace App

A beautiful and modern Android marketplace app built with Jetpack Compose, specializing in handmade and artisanal goods.

## 🎨 Design System

### Color Palette
- **Primary**: Saddle Brown (#8B4513)
- **Secondary**: Tan (#D2B48C)
- **Accent**: Beige (#F5F5DC)
- **Success**: Green (#22C55E)
- **Warning**: Amber (#F59E0B)
- **Error**: Red (#EF4444)

### Typography
- **Display**: 36-57sp for large headlines
- **Headline**: 24-32sp for section titles
- **Title**: 14-22sp for card titles
- **Body**: 12-16sp for content text
- **Label**: 11-14sp for small labels

## 📱 Pages & Components

### Main Navigation
- **Home**: Featured products and categories
- **Categories**: Browse all product categories
- **Cart**: Shopping cart with checkout
- **Profile**: User account and settings

### Home Screen (`HomeScreen.kt`)
- Header with "Crafted" branding
- Featured products horizontal scroll
- Categories grid (2x2 layout)
- Search functionality

**Components:**
- `FeaturedProductCard`: Product cards with image, name, and price
- `CategoryCard`: Category cards with overlay text

### Categories Screen (`SearchScreen.kt`)
- Back navigation
- Categories grid with item counts
- 8 main categories: Pottery, Textiles, Woodwork, Jewelry, etc.

### Cart Screen (`CartScreen.kt`)
- Cart items list with quantity controls
- Price breakdown (subtotal, shipping, total)
- Checkout button
- Remove item functionality

**Components:**
- `CartItemCard`: Individual cart item with image, details, and controls

### Profile Screen (`ProfileScreen.kt`)
- User profile picture and information
- Statistics (Orders, Wishlist, Reviews)
- Menu items for account management

**Components:**
- `ProfileStat`: Statistics display
- `ProfileMenuItem`: Menu item with icon and navigation

### Product Detail Screen (`ProductDetailScreen.kt`)
- Large product images
- Product information and pricing
- Rating and reviews
- Color options
- Detailed description
- Specifications
- Add to cart functionality

**Components:**
- `ColorOption`: Color selection circles
- `SpecificationItem`: Key-value specification pairs

### Wishlist Screen (`WishlistScreen.kt`)
- Saved items list
- Product ratings and reviews
- Add to cart from wishlist
- Remove from wishlist

**Components:**
- `WishlistItemCard`: Wishlist item with actions

### Search Results Screen (`SearchResultsScreen.kt`)
- Search bar with filters
- Filter chips for categories and price ranges
- Sort options
- Product grid with ratings and discounts

**Components:**
- `SearchResultCard`: Product cards with discount badges
- `FilterChip`: Category and price filters

## 🧩 Shared Components (`SharedComponents.kt`)

### Buttons
- `CraftedButton`: Primary action button
- `CraftedOutlinedButton`: Secondary action button

### Cards & Containers
- `CraftedCard`: Standard card container
- `CraftedTextField`: Input field with styling

### Interactive Elements
- `CraftedChip`: Filter and selection chips
- `CraftedRatingBar`: Star rating display
- `CraftedBadge`: Discount and status badges
- `CraftedIconButton`: Icon buttons with optional background

### Layout Helpers
- `CraftedSpacer`: Vertical spacing
- `CraftedHorizontalSpacer`: Horizontal spacing
- `CraftedDivider`: Section dividers

### States
- `CraftedLoadingIndicator`: Loading state
- `CraftedEmptyState`: Empty state with icon and message

## 🎨 Theme System (`CraftedTheme.kt`)

### Color Schemes
- **Light Theme**: White backgrounds with brown accents
- **Dark Theme**: Dark backgrounds with light accents

### Design Tokens
- **Colors**: Complete color palette with semantic naming
- **Typography**: Comprehensive text styles
- **Shapes**: Rounded corner definitions
- **Spacing**: Consistent spacing scale

## 🚀 Features

### Core Functionality
- ✅ Product browsing and search
- ✅ Category filtering
- ✅ Shopping cart management
- ✅ Wishlist functionality
- ✅ User profile management
- ✅ Product details and specifications
- ✅ Rating and review system

### UI/UX Features
- ✅ Modern Material Design 3
- ✅ Responsive layouts
- ✅ Smooth animations
- ✅ Accessibility support
- ✅ Dark/Light theme support
- ✅ Consistent design system

### Navigation
- ✅ Bottom navigation
- ✅ Top app bar with actions
- ✅ Back navigation
- ✅ Search functionality

## 📁 Project Structure

```
app/src/main/java/com/example/blogapp/view/
├── pages/
│   ├── Home Screen.kt          # Home screen with featured products
│   ├── SearchScreen.kt         # Categories screen
│   ├── ProfileScreen.kt        # User profile screen
│   ├── CartScreen.kt           # Shopping cart screen
│   ├── ProductDetailScreen.kt  # Product details screen
│   ├── WishlistScreen.kt       # Wishlist screen
│   └── SearchResultsScreen.kt  # Search results screen
├── components/
│   └── SharedComponents.kt     # Reusable UI components
├── theme/
│   └── CraftedTheme.kt         # Design system and theming
└── NavigationActivity.kt       # Main navigation container
```

## 🛠️ Technical Stack

- **UI Framework**: Jetpack Compose
- **Language**: Kotlin
- **Architecture**: MVVM with Compose
- **Design System**: Material Design 3
- **Theme**: Custom Crafted theme
- **Navigation**: Compose Navigation

## 🎯 Key Design Principles

1. **Artisanal Aesthetic**: Warm, earthy colors reflecting handmade goods
2. **User-Centric**: Intuitive navigation and clear product information
3. **Accessibility**: High contrast and readable typography
4. **Performance**: Efficient layouts and smooth animations
5. **Consistency**: Unified design system across all components

## 🔧 Getting Started

1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Run the app on an emulator or device
4. Navigate through the different screens to explore the UI

## 📸 Screenshots

The app includes the following main screens:
- Home with featured products and categories
- Categories browsing
- Shopping cart with checkout
- User profile and settings
- Product details with specifications
- Wishlist management
- Search results with filtering

## 🤝 Contributing

This UI implementation provides a solid foundation for a marketplace app. You can extend it by:
- Adding more product categories
- Implementing actual data binding
- Adding payment integration
- Including user authentication
- Adding more interactive features

## 📄 License

This project is for educational and demonstration purposes. 