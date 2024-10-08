package com.braze.ui.widget;

import android.content.Context;
import android.widget.ImageView;

import com.braze.models.cards.BannerImageCard;
import com.braze.ui.R;
import com.braze.ui.feed.view.BaseFeedCardView;
import com.braze.support.BrazeLogger;
import com.braze.ui.actions.IAction;

public class BannerImageCardView extends BaseFeedCardView<BannerImageCard> {
  private static final String TAG = BrazeLogger.getBrazeLogTag(BannerImageCardView.class);
  private final ImageView mImage;
  private IAction mCardAction;

  // We set this card's aspect ratio here as a first guess. If the server doesn't send down an
  // aspect ratio, then this value will be the aspect ratio of the card on render.
  private float mAspectRatio = 6f;

  public BannerImageCardView(Context context) {
    this(context, null);
  }

  public BannerImageCardView(final Context context, BannerImageCard card) {
    super(context);
    mImage = (ImageView) getProperViewFromInflatedStub(R.id.com_braze_banner_image_card_imageview_stub);
    mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    mImage.setAdjustViewBounds(true);

    if (card != null) {
      setCard(card);
    }

    setBackground(getResources().getDrawable(R.drawable.com_braze_card_background, null));
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.com_braze_banner_image_card;
  }

  @Override
  public void onSetCard(final BannerImageCard card) {
    if (card.getAspectRatio() != 0f) {
      mAspectRatio = card.getAspectRatio();
    }
    setImageViewToUrl(mImage, card.getImageUrl(), mAspectRatio, card);

    mCardAction = getUriActionForCard(card);
    setOnClickListener(view -> handleCardClick(applicationContext, card, mCardAction));
  }
}
